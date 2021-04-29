package com.monese.bank.controller;

import com.monese.bank.model.Account;
import com.monese.bank.model.AccountStatement;
import com.monese.bank.model.Response;
import com.monese.bank.repository.IAccount;
import com.monese.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private IAccount accountDAO;

    @Autowired
    private AccountService accountService;

    @PostMapping(value = "/saveAccount", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Account saveAccount(@RequestBody Account account) throws Exception {
        return accountDAO.saveAccount(account);
    }

    @GetMapping(value = "/balance", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Float> balance(@RequestParam int accountId) {
        Response response = accountService.getResponse(accountId);
        if (!response.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(accountDAO.getBalance(accountId), HttpStatus.OK);
        }
    }
}
