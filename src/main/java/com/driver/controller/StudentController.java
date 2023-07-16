package com.driver.controller;

import com.driver.models.Student;
import com.driver.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/studentByEmail/")
    public ResponseEntity<String> getStudentByEmail(@RequestParam("email") String email) {
        Student student = studentService.getDetailsByEmail(email);
        if (student != null) {
            return new ResponseEntity<>("Student details: " + student.toString(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Student not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/studentById/")
    public ResponseEntity<String> getStudentById(@RequestParam("id") int id) {
        Student student = studentService.getDetailsById(id);
        if (student != null) {
            return new ResponseEntity<>("Student details: " + student.toString(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Student not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public ResponseEntity<String> createStudent(@RequestBody Student student) {
        studentService.createStudent(student);
        return new ResponseEntity<>("Student is successfully added to the system", HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<String> updateStudent(@RequestBody Student student) {
        studentService.updateStudent(student);
        return new ResponseEntity<>("Student is updated", HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/")
    public ResponseEntity<String> deleteStudent(@RequestParam("id") int id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>("Student is deleted", HttpStatus.ACCEPTED);
    }
}
