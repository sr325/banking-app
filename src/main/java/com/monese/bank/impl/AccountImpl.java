package com.monese.bank.impl;

import com.monese.bank.model.Account;
import com.monese.bank.repository.IAccount;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class AccountImpl implements IAccount {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public Account saveAccount(final Account account) throws Exception {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Session session = entityManager.unwrap(Session.class);

        try {
            entityManager.getTransaction().begin();
            account.setTransactions(null);
            session.saveOrUpdate(account);
            entityManager.flush();
            entityManager.getTransaction().commit();
            return account;
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            throw ex;
        } finally {
            session.close();
            entityManager.close();
        }
    }


    @Override
    public Float getBalance(int accountId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Session session = entityManager.unwrap(Session.class);

        try {
            entityManager.getTransaction().begin();
            Account account = entityManager.find(Account.class, accountId);
            return account.getBalance();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            throw ex;
        } finally {
            session.close();
            entityManager.close();
        }
    }

    @Override
    public Account getAccount(int accountId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Session session = entityManager.unwrap(Session.class);

        try {
            entityManager.getTransaction().begin();
            Account account = entityManager.find(Account.class, accountId);
            return account;
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            throw ex;
        } finally {
            session.close();
            entityManager.close();
        }
    }
}
