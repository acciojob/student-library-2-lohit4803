package com.driver.models;

import java.util.ArrayList;
import java.util.List;

public class Card {
    private Long id;
    private com.library.models.Student student;
    private CardStatus cardStatus;
    private List<Book> books;  // Add the List<Book> field

    // Constructor, getters, and setters

    public Card(Long id, com.library.models.Student student, CardStatus cardStatus) {
        this.id = id;
        this.student = student;
        this.cardStatus = cardStatus;
        this.books = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public com.library.models.Student getStudent() {
        return student;
    }

    public void setStudent(com.library.models.Student student) {
        this.student = student;
    }

    public CardStatus getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(CardStatus cardStatus) {
        this.cardStatus = cardStatus;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
