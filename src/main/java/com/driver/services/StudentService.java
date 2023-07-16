package com.driver.services;

import com.driver.models.Student;
import com.driver.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    CardService cardService;

    @Autowired
    StudentRepository studentRepository;

    public Student getDetailsByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    public Student getDetailsById(int id) {
        return studentRepository.findById(id);
    }

    public void createStudent(Student student) {
        studentRepository.save(student);
    }

    public void updateStudent(Student student) {
        studentRepository.update(student);
    }

    public void deleteStudent(int id) {
        Student student = studentRepository.findById(id);
        if (student != null) {
            cardService.deactivateCard(id);
            studentRepository.delete(student);
        }
    }
}
