package com.example.school.controller;

import com.example.school.dto.TeacherDto;
import com.example.school.dto.input.AddTeacherDto;
import com.example.school.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "teachers", description = "Operations on teachers")
@RestController
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @Operation(summary = "Add new teacher.")
    @ApiResponse(responseCode = "200", description = "New teacher successfully added.")
    @PostMapping("teacher")
    public TeacherDto addTeacher(@RequestBody AddTeacherDto teacherDto) {
        return teacherService.addTeacher(teacherDto);
    }

    @Operation(summary = "List all teachers.")
    @ApiResponse(responseCode = "200", description = "Found all teachers.")
    @GetMapping("teachers")
    public List<TeacherDto> getTeachers() {
        return teacherService.getAll();
    }

}
