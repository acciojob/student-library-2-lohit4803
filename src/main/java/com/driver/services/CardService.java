package com.driver.services;

import com.driver.models.Student;
import com.driver.models.Card;
import com.driver.models.CardStatus;
import com.driver.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    @Autowired
    CardRepository cardRepository;

    public Card createAndReturn(Student student) {
        Card card = new Card(student);
        cardRepository.save(card);
        return card;
    }

    public void deactivateCard(int studentId) {
        cardRepository.deactivateCard(studentId, CardStatus.DEACTIVATED);
    }
}
