package com.monese.bank.service;

import com.monese.bank.model.Response;
import com.monese.bank.repository.ITransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    private int senderId;
    private float senderAmount;
    private int receiverId;

    @Autowired
    private ITransaction transactionDAO;

    public TransactionService() {
    }

    public TransactionService(int senderId, float senderAmount, int receiverId) {
        this.senderId = senderId;
        this.senderAmount = senderAmount;
        this.receiverId = receiverId;
    }

    public Response getTransferResponse(int senderId, float senderAmount, int receiverId) {
        Response rp = new Response();

        if (senderId == 0 || receiverId == 0) {
            rp.setSuccess(false);
            rp.setMessage("The sender and receiver needs to be valid");
            return rp;
        }

        if (senderId == receiverId) {
            rp.setSuccess(false);
            rp.setMessage("You cannot send money to yourself");
            return rp;
        }

        if (senderAmount < 0.01) {
            rp.setSuccess(false);
            rp.setMessage("The amount should be more than 0.01");
            return rp;
        }

        rp.setSuccess(true);
        float senderBalance = transactionDAO.transfer(senderId, senderAmount, receiverId);
        String notification = "Balance of " + senderId +  " is " + senderBalance;
        rp.setMessage(notification);
        return rp;
    }
}
