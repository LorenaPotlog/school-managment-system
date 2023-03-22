package com.example.school.controller;

import com.example.school.model.Student;
import com.example.school.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("getStudents")
    public List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        for (Student student : studentRepository.findAll()) {
            students.add(student);
        }
        return students;
    }
}
