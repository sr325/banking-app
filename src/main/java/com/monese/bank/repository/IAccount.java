package com.monese.bank.repository;

import com.monese.bank.model.Account;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccount {
    Account saveAccount(Account account) throws Exception;

    Account getAccount(int accountId);

    Float getBalance(int accountId);
}
