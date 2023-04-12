package com.example.school.controller;

import com.example.school.controller.util.Response;
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
import org.webjars.NotFoundException;

import java.util.List;

@Tag(name = "teachers", description = "Operations on teachers")
@RestController
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @Operation(summary = "List all teachers.")
    @ApiResponse(responseCode = "200", description = "Found all teachers.")
    @GetMapping("teachers")
    public List<TeacherDto> getTeachers() {
        return teacherService.getAll();

    }

    @Operation(summary = "Add new teacher.")
    @ApiResponse(responseCode = "200", description = "New teacher successfully added.")
    @ApiResponse(responseCode = "404", description = "Group/School not found.")
    @PostMapping("teacher")
    public Response addTeacher(@RequestBody AddTeacherDto addTeacherDto) {
        Response response = new Response();
        try {
            response.setContent(teacherService.add(addTeacherDto));
        } catch (NotFoundException e) {
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }
}
