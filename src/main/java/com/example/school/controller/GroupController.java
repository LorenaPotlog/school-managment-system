package com.example.school.controller;

import com.example.school.dto.StudentDto;
import com.example.school.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GroupController {

    @Autowired
    private GroupService groupService;

    /**
     * returnes all students within the same class (group)
     **/
    @GetMapping("class/{className}/students")
    public List<StudentDto> getStudentsFromClass(@PathVariable("className") String className) {
        return groupService.getStudents(className);
    }

    /**
     * deletes student form class
     **/
    @DeleteMapping("deleteStudentFromClass")
    public ResponseEntity<String> deleteStudent(@RequestParam("className") String className, @RequestParam("id") Long studentId) {
        try {
            groupService.deleteStudent(className, studentId);
            return new ResponseEntity<>("Successfully deleted student.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}