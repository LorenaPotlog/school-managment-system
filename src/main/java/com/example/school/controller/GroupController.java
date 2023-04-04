package com.example.school.controller;

import com.example.school.dto.GroupDto;
import com.example.school.dto.StudentDto;
import com.example.school.dto.input.AddGroupDto;
import com.example.school.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "groups", description = "Operations on groups")

@RestController
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Operation(summary = "Add new group.")
    @ApiResponse(responseCode = "200", description = "New group successfully added.")
    @PostMapping("class")
    public GroupDto addGroup(@RequestBody AddGroupDto addGroupDto) {
        return groupService.addGroup(addGroupDto);

    }

    @Operation(summary = "List all students in class.")
    @ApiResponse(responseCode = "200", description = "Found all students.")
    @GetMapping("classes/{class}/students")
    public List<StudentDto> getStudentsFromClass(@Parameter(description = "Class name") @PathVariable("class") String className) {
        return groupService.getStudents(className);
    }

    @Operation(summary = "Delete student from class")
    @ApiResponse(responseCode = "200", description = "Student was successfully deleted.")
    @ApiResponse(responseCode = "404", description = "Student not found.")
    @DeleteMapping("classes/{class}/students/{id}")
    public ResponseEntity<String> deleteStudent(@Parameter(description = "Class name") @PathVariable("class") String className, @Parameter(description = "Student Id") @PathVariable("id") Long studentId) {
        try {
            groupService.deleteStudent(className, studentId);
            return new ResponseEntity<>("Student was successfully deleted.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}