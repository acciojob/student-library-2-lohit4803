package com.library.models;

import com.driver.models.Card;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int age;

    private String country;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Card> cards = new ArrayList<>();

    // Constructors, getters, and setters

    // ...
}
