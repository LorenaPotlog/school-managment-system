package com.example.school.controller;

import com.example.school.controller.util.Response;
import com.example.school.dto.SchoolDto;
import com.example.school.dto.input.AddSchoolDto;
import com.example.school.service.SchoolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.List;

@Tag(name = "schools", description = "Operations on schools")
@RestController
public class SchoolController {
    @Autowired
    private SchoolService schoolService;

    @Operation(summary = "List all schools.")
    @ApiResponse(responseCode = "200", description = "Found all schools.")
    @GetMapping("schools")
    public List<SchoolDto> getSchools() {
        return schoolService.getAll();
    }

    @Operation(summary = "Add new school.")
    @ApiResponse(responseCode = "200", description = "New school successfully added.")
    @ApiResponse(responseCode = "404", description = "Teacher not found.")
    @PostMapping("school")
    public Response addSchool(@RequestBody AddSchoolDto addSchoolDto) {
        Response response = new Response();
        try {
            response.setContent(schoolService.add(addSchoolDto));
        } catch (NotFoundException e) {
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @Operation(summary = "List all groups in school.")
    @ApiResponse(responseCode = "200", description = "Found all groups in school.")
    @ApiResponse(responseCode = "404", description = "School not found.")
    @GetMapping("school/{school}/groups")
    public Response getGroupsFromSchool(@Parameter(description = "School name") @PathVariable("school") String schoolName) {
        Response response = new Response();
        try {
            response.setContent(schoolService.getGroups(schoolName));
        } catch (NotFoundException e) {
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }
}
