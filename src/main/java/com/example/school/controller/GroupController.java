package com.example.school.controller;

import com.example.school.model.Group;
import com.example.school.model.Student;
import com.example.school.repository.GroupRepository;
import com.example.school.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class GroupController {

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private StudentRepository studentRepository;


    /**
     * returnes all students within the same class (group)
     **/
    @GetMapping("class/{className}/students")
    public List<Student> getStudentsFromClass(@PathVariable("className") String className) {
        List<Student> studentsFromClass = new ArrayList<>();
        for (Student student : groupRepository.findByGroupName(className).get().getStudents()) {
            studentsFromClass.add(student);
        }
        return studentsFromClass;
    }

    /**
     * deletes student form class
     **/
    @DeleteMapping("deleteStudentFromClass")
    public ResponseEntity<String> deleteStudent(@RequestParam("className") String className, @RequestParam("id") Long studentId) {
        Optional<Group> currentClass = groupRepository.findByGroupName(className);
        Optional<Student> existingStudent = studentRepository.findById(studentId);
        if (existingStudent.isEmpty()) {
            return new ResponseEntity<>("Student not found.", HttpStatus.NOT_FOUND);
        }

        if (currentClass.isEmpty()) {
            return new ResponseEntity<>("Class not found.", HttpStatus.NOT_FOUND);
        }

        for (Student student : currentClass.get().getStudents()) {
            if (existingStudent.get() == student) {
                studentRepository.delete(existingStudent.get());
                return new ResponseEntity<>("Successfully deleted student.", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Student not found in class.", HttpStatus.NOT_FOUND);

    }
}