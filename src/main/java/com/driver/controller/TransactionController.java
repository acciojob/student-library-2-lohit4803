package com.driver.controller;

import com.driver.models.Transaction;
import com.driver.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/issueBook")
    public ResponseEntity<String> issueBook(@RequestParam("cardId") Long cardId, @RequestParam("bookId") Long bookId) throws Exception {
        String result = transactionService.issueBook(cardId, bookId);
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }

    @PostMapping("/returnBook")
    public ResponseEntity<String> returnBook(@RequestParam("cardId") Long cardId, @RequestParam("bookId") Long bookId) throws Exception {
        String result = transactionService.returnBook(cardId, bookId);
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }
}
