package com.monese.bank.impl;

import com.monese.bank.model.Account;
import com.monese.bank.model.AccountStatement;
import com.monese.bank.model.Transaction;
import com.monese.bank.model.TransactionType;
import com.monese.bank.repository.ITransaction;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Date;

public class TransactionImp implements ITransaction {
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public AccountStatement accountStatement(int accountId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Session session = entityManager.unwrap(Session.class);
        try {
            entityManager.getTransaction().begin();
            Account account = entityManager.find(Account.class, accountId);
            AccountStatement accountStatement = new AccountStatement();
            accountStatement.setAccountId(account.getId());
            accountStatement.setBalance(account.getBalance());
            accountStatement.setTransactions(account.getTransactions());
            return accountStatement;
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            throw ex;
        } finally {
            session.close();
            entityManager.close();
        }
    }

    @Override
    public float transfer(int senderId, float senderAmount, int receiverId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Session session = entityManager.unwrap(Session.class);
        try {
            entityManager.getTransaction().begin();
            Account senderAccount = entityManager.find(Account.class, senderId);
            float senderCurrentBalance = senderAccount.getBalance();
            float newSenderBalance = senderCurrentBalance - senderAmount;
            senderAccount.setBalance(newSenderBalance);

            Transaction senderTransaction = new Transaction();
            senderTransaction.setAmount(senderAmount);
            senderTransaction.setTransactionAt(new Date());
            senderTransaction.setTransactionType(TransactionType.CREDIT);
            senderAccount.getTransactions().add(senderTransaction);
            session.saveOrUpdate(senderAccount);

            Account receiverAccount = entityManager.find(Account.class, receiverId);
            float receiverCurrentBalance = receiverAccount.getBalance();
            float newReceiverBalance = receiverCurrentBalance + senderAmount;
            receiverAccount.setBalance(newReceiverBalance);

            Transaction receiverTransaction = new Transaction();
            receiverTransaction.setAmount(senderAmount);
            receiverTransaction.setTransactionAt(new Date());
            receiverTransaction.setTransactionType(TransactionType.DEBIT);
            receiverAccount.getTransactions().add(receiverTransaction);
            session.saveOrUpdate(receiverAccount);

            entityManager.getTransaction().commit();
            return newSenderBalance;
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            throw ex;
        } finally {
            session.close();
            entityManager.close();
        }
    }

    public Transaction addTransaction(int accountId, Transaction trans) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Session session = entityManager.unwrap(Session.class);

        try {
            entityManager.getTransaction().begin();
            Account account = entityManager.find(Account.class, accountId);
            session.saveOrUpdate(trans);
            account.getTransactions().add(trans);
            session.saveOrUpdate(account);
            entityManager.flush();
            entityManager.getTransaction().commit();
            return trans;
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            throw ex;
        } finally {
            session.close();
            entityManager.close();
        }
    }
}
