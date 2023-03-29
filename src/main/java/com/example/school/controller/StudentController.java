package com.example.school.controller;

import com.example.school.dto.StudentDto;
import com.example.school.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * Returnes all students
     **/
    @GetMapping("students")
    public List<StudentDto> getStudents() {
        return studentService.getAll();
    }

    /**
     * Adds new student
     **/
    @PostMapping("newStudent") //Post
    public StudentDto addNewStudent(@RequestParam("firstname") String firstName, @RequestParam("lastname") String lastName) {
        return studentService.add(firstName, lastName);
    }

    /**
     * Moves or enrolls student to a class (group)
     **/
    @PutMapping("updateClass")
    public StudentDto updateStudentByClass(@RequestParam("id") Long studentId, @RequestParam("classId") Long groupId) {
        return studentService.changeClass(studentId, groupId);
    }

    /**
     * Transfers student to a new school
     * any transfer will set the class (group) field to null
     **/
    @PutMapping("changeSchool")
    public StudentDto transferStudent(@RequestParam("id") Long studentId, @RequestParam("schoolId") Long schoolId) {
        return studentService.changeSchool(studentId, schoolId);
    }

    /**
     * Transfers student to a new school and enroll in class (group)
     **/
    @PutMapping("transferAndEnrollInClass/{classname}")
    public StudentDto transferStudentAndEnroll(@RequestParam("id") Long studentId, @RequestParam("schoolId") Long schoolId, @PathVariable("classname") String groupName) {
        return studentService.transferAndEnroll(studentId, schoolId, groupName);
    }
}

