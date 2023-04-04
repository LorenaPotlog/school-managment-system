package com.example.school.controller;

import com.example.school.dto.StudentDto;
import com.example.school.dto.input.AddStudentDto;
import com.example.school.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "students", description = "Operations on students")
@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Operation(summary = "List all students.")
    @ApiResponse(responseCode = "200", description = "Found all students.")
    @GetMapping("students")
    public List<StudentDto> getStudents() {
        return studentService.getAll();
    }

    @Operation(summary = "Add new student.")
    @ApiResponse(responseCode = "200", description = "New student successfully added.")
    @PostMapping("student")
    public StudentDto addNewStudent(@RequestBody AddStudentDto addStudentDto) {
        return studentService.addStudent(addStudentDto);
    }

    @Operation(summary = "Add or change class.")
    @ApiResponse(responseCode = "200", description = "Class was successfully changed.")
    @ApiResponse(responseCode = "404", description = "Class/Student not found")
    @PatchMapping("students/{id}/class/{class}")
    public StudentDto updateClass(@Parameter(description = "Student Id") @PathVariable("id") Long studentId, @Parameter(description = "New class Id") @PathVariable("class") Long groupId) {
        return studentService.changeClass(studentId, groupId);
    }

    @Operation(summary = "Add or change school.")
    @ApiResponse(responseCode = "200", description = "School was successfully changed.")
    @ApiResponse(responseCode = "404", description = "School/Student not found")
    @PatchMapping("students/{id}/school/{school}")
    public StudentDto updateSchool(@Parameter(description = "Student Id") @PathVariable("id") Long studentId, @Parameter(description = "New school Id") @PathVariable("school") Long schoolId) {
        return studentService.changeSchool(studentId, schoolId);
    }

    @Operation(summary = "Add or change school and class.")
    @ApiResponse(responseCode = "200", description = "School and class was successfully changed.")
    @ApiResponse(responseCode = "404", description = "School/Class/Student not found")
    @PatchMapping("students/{id}/school/{school}/class/{class}")
    public StudentDto updateSchoolAndClass(@Parameter(description = "Student Id") @PathVariable("id") Long studentId, @Parameter(description = "New school Id") @PathVariable("school") Long schoolId, @Parameter(description = "New class name") @PathVariable("class") String groupName) {
        return studentService.changeSchoolAndClass(studentId, schoolId, groupName);
    }
}

