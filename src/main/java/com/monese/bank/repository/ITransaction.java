package com.monese.bank.repository;

import com.monese.bank.model.AccountStatement;
import com.monese.bank.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITransaction {

    float transfer(int senderId, float senderAmount, int receiverId);

    AccountStatement accountStatement(int accountId);
}
