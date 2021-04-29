package com.monese.bank.controller;

import com.monese.bank.model.AccountStatement;
import com.monese.bank.model.Response;
import com.monese.bank.repository.ITransaction;
import com.monese.bank.service.AccountService;
import com.monese.bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;

    @PostMapping(value = "/transfer")
    @ResponseBody
    public ResponseEntity<String> transfer(@RequestParam int senderId, @RequestParam float senderAmount, @RequestParam int receiverId) {
        Response response = transactionService.getTransferResponse(senderId, senderAmount, receiverId);
        if (!response.isSuccess()) {
            return new ResponseEntity<>(response.getMessage(), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(response.getMessage(), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/accountStatement", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountStatement> accountStatement(@RequestParam int accountId) {
        Response response = accountService.getResponse(accountId);
        if (!response.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(accountService.getAccountStatement(accountId), HttpStatus.OK);
        }
    }
}
