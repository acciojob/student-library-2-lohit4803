package com.driver.services;

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
        LocalDate dueDate = transaction.getIssuedDate().plusDays(maxAllowedDays);

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

        Transaction transaction = new Transaction(cardId, bookId);
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

        Transaction returnBookTransaction = new Transaction(cardId, bookId, fineAmount);
        transactionRepository.save(returnBookTransaction);

        bookRepository.makeBookAvailable(bookId);
        return returnBookTransaction;
    }
}
