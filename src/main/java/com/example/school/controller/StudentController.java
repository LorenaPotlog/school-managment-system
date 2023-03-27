package com.example.school.controller;

import com.example.school.model.Group;
import com.example.school.model.School;
import com.example.school.model.Student;
import com.example.school.repository.GroupRepository;
import com.example.school.repository.SchoolRepository;
import com.example.school.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    /**
     * Returnes all students
     **/
    @GetMapping("students")
    public List<Student> getStudents() {

        List<Student> students = new ArrayList<>();
        for (Student student : studentRepository.findAll()) {
            students.add(student);
        }

        return students;
    }

    /**
     * Adds new student
     **/
    @PostMapping("newStudent") //Post
    public Student addNewStudent(@RequestParam("firstname") String firstName, @RequestParam("lastname") String lastName) {

        Student newStudent = new Student();
        newStudent.setFirstName(firstName);
        newStudent.setLastName(lastName);

        return studentRepository.save(newStudent);
    }

    /**
     * Moves or enrolls student to a class (group)
     **/
    @PutMapping("updateClass")
    public Student updateStudentByClass(@RequestParam("id") Long studentId, @RequestParam("classId") Long groupId) {
        Student existingStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Student not found"));
        Group updatedGroup = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Group not found"));
        existingStudent.setGroup(updatedGroup);

        return studentRepository.save(existingStudent);

    }

    /**
     * Transfers student to a new school
     * any transfer will set the class (group) field to null
     **/
    @PutMapping("changeSchool")
    public Student transferStudent(@RequestParam("id") Long studentId, @RequestParam("schoolId") Long schoolId) {
        Student transferredStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Student not found"));
        School newSchool = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new NotFoundException("School not found"));
        transferredStudent.setGroup(null);
        transferredStudent.setSchool(newSchool);
        return studentRepository.save(transferredStudent);

    }

    /**
     * Transfers student to a new school and enroll in class (group)
     **/
    @PutMapping("transferAndEnrollInClass/{classname}")
    public Student transferStudentAndEnroll(@RequestParam("id") Long studentId, @RequestParam("schoolId") Long schoolId, @PathVariable("classname") String groupName) {
        Student transferredStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Student not found"));
        School newSchool = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new NotFoundException("School not found"));
        transferredStudent.setSchool(newSchool);

        Group updatedGroup = groupRepository.findByGroupName(groupName)
                .orElseThrow(() -> new NotFoundException("Class not found"));
        if (updatedGroup.getSchool() == newSchool) {
            transferredStudent.setGroup(updatedGroup);
        } else {
            transferredStudent.setGroup(null);
        }

        return studentRepository.save(transferredStudent);
    }

}

