package com.monese.bank.service;

import com.monese.bank.model.Account;
import com.monese.bank.model.AccountStatement;
import com.monese.bank.model.Response;
import com.monese.bank.repository.IAccount;
import com.monese.bank.repository.ITransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private int accountId;

    @Autowired
    private ITransaction transactionDAO;

    @Autowired
    private IAccount accountDAO;

    public AccountService() {
    }

    public AccountService(int accountId) {
        this.accountId = accountId;
    }

    public Response getResponse(final int accountId) {
        Response rp = new Response();

        Account account = accountDAO.getAccount(accountId);
        if (account == null) {
            rp.setSuccess(false);
            rp.setMessage("Account does not exist");
            return rp;
        }

        rp.setSuccess(true);
        return rp;
    }

    public AccountStatement getAccountStatement(final int accountId) {
        return transactionDAO.accountStatement(accountId);
    }
}
