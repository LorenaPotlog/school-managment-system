package com.example.school.controller;

import com.example.school.model.School;
import com.example.school.model.Student;
import com.example.school.repository.GroupRepository;
import com.example.school.repository.SchoolRepository;
import com.example.school.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController

public class SchoolController {
    @Autowired
    private SchoolRepository schoolRepository;
    private StudentRepository studentRepository;

    @GetMapping("schools")
    public List<School> getSchools() {
        List<School> schools = new ArrayList<>();
        for (School school : schoolRepository.findAll()) {
            schools.add(school);
        }
        return schools;
}
}
