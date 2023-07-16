package com.driver.services;

import com.driver.models.Book;
import com.driver.models.Card;
import com.driver.models.Transaction;
import com.driver.models.TransactionStatus;
import com.driver.repositories.BookRepository;
import com.driver.repositories.CardRepository;
import com.driver.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CardRepository cardRepository;
    private final BookRepository bookRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, CardRepository cardRepository, BookRepository bookRepository) {
        this.transactionRepository = transactionRepository;
        this.cardRepository = cardRepository;
        this.bookRepository = bookRepository;
    }

    public String issueBook(Long cardId, Long bookId) {
        // Retrieve the card and book from their respective repositories
        Card card = cardRepository.findById(cardId).orElse(null);
        Book book = bookRepository.findById(bookId).orElse(null);

        // Check if the card and book exist
        if (card == null || book == null) {
            return "Card or book not found";
        }

        // Check if the book is already issued to the card
        if (book.getCard() != null) {
            return "Book is already issued to another card";
        }

        // Issue the book to the card
        book.setCard(card);
        card.getBooks().add(book);
        transactionRepository.save(new Transaction(card, book, TransactionStatus.ISSUED));

        return "Book issued successfully";
    }

    public String returnBook(Long cardId, Long bookId) {
        // Retrieve the card and book from their respective repositories
        Card card = cardRepository.findById(cardId).orElse(null);
        Book book = bookRepository.findById(bookId).orElse(null);

        // Check if the card and book exist
        if (card == null || book == null) {
            return "Card or book not found";
        }

        // Check if the book is issued to the card
        if (book.getCard() == null || !book.getCard().equals(card)) {
            return "Book is not issued to the specified card";
        }

        // Remove the book from the card and update its availability
        card.getBooks().remove(book);
        book.setCard(null);

        // Save the transaction
        transactionRepository.save(new Transaction(card, book, TransactionStatus.RETURNED));

        return "Book returned successfully";
    }

    // Implement other service methods for Transaction

    // ...
}
