package com.example.school.controller;

import com.example.school.controller.util.Response;
import com.example.school.dto.StudentDto;
import com.example.school.dto.input.AddStudentDto;
import com.example.school.dto.input.TransferStudentDto;
import com.example.school.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

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
    @ApiResponse(responseCode = "404", description = "School/Group not found.")
    @PostMapping("student")
    public Response addNewStudent(@RequestBody AddStudentDto addStudentDto) {
        Response response = new Response();
        try {
            response.setContent(studentService.add(addStudentDto));
        } catch (NotFoundException e) {
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @Operation(summary = "Delete student from group.")
    @ApiResponse(responseCode = "200", description = "Student was successfully deleted.")
    @ApiResponse(responseCode = "404", description = "Student/Group not found.")
    @DeleteMapping("student/{id}/group/{name}")
    public ResponseEntity<String> deleteStudent(@Parameter(description = "Group name") @PathVariable("name") String groupName, @Parameter(description = "Student Id") @PathVariable("id") Long studentId) {
        try {
            studentService.delete(groupName, studentId);
            return new ResponseEntity<>("Student was successfully deleted.", HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Add or change group.")
    @ApiResponse(responseCode = "200", description = "Group was successfully changed.")
    @ApiResponse(responseCode = "404", description = "Group/Student not found")
    @PatchMapping("student/{id}/group/{group}")
    public Response updateStudentByGroup(@Parameter(description = "Student Id") @PathVariable("id") Long studentId, @Parameter(description = "New group Id") @PathVariable("group") Long groupId) {
        Response response = new Response();
        try {
            response.setContent(studentService.changeGroup(studentId, groupId));
        } catch (NotFoundException e) {
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @Operation(summary = "Add or change school.")
    @ApiResponse(responseCode = "200", description = "School was successfully changed.")
    @ApiResponse(responseCode = "404", description = "School/Student not found")
    @PatchMapping("student/{id}/school/{school}")
    public Response updateStudentBySchool(@Parameter(description = "Student Id") @PathVariable("id") Long studentId, @Parameter(description = "New school Id") @PathVariable("school") Long schoolId) {
        Response response = new Response();
        try {
            response.setContent(studentService.changeSchool(studentId, schoolId));
        } catch (NotFoundException e) {
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @Operation(summary = "Add or change school and group.")
    @ApiResponse(responseCode = "200", description = "School and group was successfully changed.")
    @ApiResponse(responseCode = "404", description = "School/Group/Student not found")
    @PatchMapping("student")
    public Response updateStudentBySchoolAndGroup(@RequestBody TransferStudentDto transferStudentDto) {
        Response response = new Response();
        try {
            response.setContent(studentService.changeSchoolAndGroup(transferStudentDto));
        } catch (NotFoundException e) {
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }
}

