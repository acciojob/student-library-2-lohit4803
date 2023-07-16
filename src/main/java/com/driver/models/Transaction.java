package com.driver.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private Date transactionDate;

    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    public Transaction() {
        // Default constructor required by JPA
    }

    public Transaction(Card card, Book book, TransactionStatus transactionStatus) {
        this.card = card;
        this.book = book;
        this.transactionStatus = transactionStatus;
        this.transactionDate = new Date();
    }

    // Getters and setters

    // ...
}
