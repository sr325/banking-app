package com.monese.bank.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TRANSACTION")
public class Transaction {
    @Id
    @GeneratedValue(generator = "TRANSACTION_PK_SQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "TRANSACTION_PK_SQ", allocationSize = 1)
    @Column(name = "id")
    private int transactionId;

    @Column(name = "amount", nullable = false)
    private float amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "transactionAt", nullable = false)
    private Date transactionAt;

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Date getTransactionAt() {
        return transactionAt;
    }

    public void setTransactionAt(Date transactionAt) {
        this.transactionAt = transactionAt;
    }
}
