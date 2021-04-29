package com.monese.bank;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monese.bank.controller.TransactionController;
import com.monese.bank.model.Account;
import com.monese.bank.model.Response;
import com.monese.bank.repository.ITransaction;
import com.monese.bank.service.TransactionService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.StringWriter;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(MockitoJUnitRunner.class)
public class TransferTest {

    private MockMvc mockMvc;

    @InjectMocks
    TransactionController transactionController;

    @Mock
    TransactionService transactionService;

    @Mock
    ITransaction transactionDAO;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    public void testTransferMoney() throws Exception {
        Account tomAccount = new Account();
        tomAccount.setId(1);
        tomAccount.setName("Tom");
        tomAccount.setBalance(100f);
        tomAccount.setCreatedAt(new Date());

        Account jerryAccount = new Account();
        jerryAccount.setId(1);
        jerryAccount.setName("Jerry");
        jerryAccount.setBalance(100f);
        jerryAccount.setCreatedAt(new Date());

        float transaction = tomAccount.getBalance() - 10f;
        String notification = "Balance of " + tomAccount.getId() +  " is " + transaction;

        Response response = new Response();
        response.setSuccess(true);
        response.setMessage(notification);

        tomAccount.setBalance(transaction);
        jerryAccount.setBalance(jerryAccount.getBalance() + 10f);

        when(transactionService.getTransferResponse(anyInt(), anyFloat(), anyInt())).thenReturn(response);
        mockMvc.perform(post("/api/transfer/").contentType(MediaType.APPLICATION_JSON)
                        .param("senderId", "1")
                        .param("senderAmount", "10")
                        .param("receiverId", "2"))
                        .andReturn().getResponse().getStatus();

        assertEquals((int)tomAccount.getBalance(), (int)90f);
        assertEquals((int)jerryAccount.getBalance(), (int)110f);
    }
}
