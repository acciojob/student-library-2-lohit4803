package com.driver.services;

import com.driver.models.Book;
import com.driver.models.Card;
import com.driver.models.Transaction;
import com.driver.models.TransactionStatus;
import com.driver.repositories.BookRepository;
import com.driver.repositories.CardRepository;
import com.driver.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Value("${books.max_allowed}")
    private int maxAllowedBooks;

    @Value("${books.max_allowed_days}")
    private int maxAllowedDays;

    @Value("${books.fine.per_day}")
    private int finePerDay;

    public int calculateFineAmount(Transaction transaction) {
        LocalDate returnDate = LocalDate.now();
        LocalDate dueDate = transaction.getTransactionDate().plusDays(maxAllowedDays);

        long daysDiff = ChronoUnit.DAYS.between(dueDate, returnDate);
        if (daysDiff > 0) {
            return (int) (daysDiff * finePerDay);
        }
        return 0;
    }

    public String issueBook(int cardId, int bookId) throws Exception {
        boolean isBookAvailable = bookRepository.isBookAvailable(bookId);
        boolean isCardActivated = cardRepository.isCardActivated(cardId);
        int issuedBooksCount = transactionRepository.getIssuedBooksCount(cardId);

        if (!isBookAvailable) {
            throw new Exception("Book is either unavailable or not present");
        }
        if (!isCardActivated) {
            throw new Exception("Card is invalid");
        }
        if (issuedBooksCount >= maxAllowedBooks) {
            throw new Exception("Book limit has reached for this card");
        }

        // Get Card and Book entities from their respective repositories
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new Exception("Invalid Card ID"));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new Exception("Invalid Book ID"));

        Transaction transaction = new Transaction(card, book);
        transactionRepository.save(transaction);
        return "Transaction successful! Transaction ID: " + transaction.getId();
    }

    public Transaction returnBook(int cardId, int bookId) throws Exception {
        List<Transaction> transactions = transactionRepository.find(cardId, bookId, TransactionStatus.SUCCESSFUL, true);
        if (transactions.isEmpty()) {
            throw new Exception("No matching transaction found");
        }

        Transaction transaction = transactions.get(transactions.size() - 1);
        int fineAmount = calculateFineAmount(transaction);

        // Update the existing transaction with the fine amount
        transaction.setFineAmount(fineAmount);
        transactionRepository.save(transaction);

        bookRepository.makeBookAvailable(bookId);
        return transaction;
    }
}
