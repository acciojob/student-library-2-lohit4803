package com.driver.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String transactionId = UUID.randomUUID().toString(); // externalId

    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties("transactions")
    private Card card;

    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties("transactions")
    private Book book;

    private int fineAmount;

    private boolean isIssueOperation;

    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    @CreationTimestamp
    private LocalDate transactionDate;

    public Transaction() {
    }

    public Transaction(Card card, Book book) {
        this.card = card;
        this.book = book;
        this.isIssueOperation = true;
        this.transactionStatus = TransactionStatus.PENDING;
    }

    public Transaction(Card card, Book book, int fineAmount) {
        this.card = card;
        this.book = book;
        this.fineAmount = fineAmount;
        this.isIssueOperation = false;
        this.transactionStatus = TransactionStatus.PENDING;
    }

    public int getId() {
        return id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Card getCard() {
        return card;
    }

    public Book getBook() {
        return book;
    }

    public int getFineAmount() {
        return fineAmount;
    }

    public boolean isIssueOperation() {
        return isIssueOperation;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }
}
