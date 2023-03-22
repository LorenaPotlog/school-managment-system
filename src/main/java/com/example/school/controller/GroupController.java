package com.example.school.controller;

import com.example.school.model.Student;
import com.example.school.repository.GroupRepository;
import com.example.school.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GroupController {

    @Autowired
    private GroupRepository groupRepository;
    private StudentRepository studentRepository;

    @GetMapping("class/{className}/students") //afiseaza elevii unei clase, dintr-o scoala
    public List<Student> getStudentsFromClass(@PathVariable("className") String className, @RequestParam("schoolName") String schoolName) {
        List<Student> studentsFromClass = new ArrayList<>();
        for (Student student : groupRepository.findByGroupName(className).get().getStudents()) {
            studentsFromClass.add(student);
        }
        return studentsFromClass;
    }
}
