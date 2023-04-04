package com.example.school.controller;

import com.example.school.dto.GroupDto;
import com.example.school.dto.SchoolDto;
import com.example.school.dto.input.AddSchoolDto;
import com.example.school.service.SchoolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.webjars.NotFoundException;

import java.util.List;

@Tag(name = "schools", description = "Operations on schools")
@RestController
public class SchoolController {
    @Autowired
    private SchoolService schoolService;

    @Operation(summary = "Add new school.")
    @ApiResponse(responseCode = "200", description = "New school successfully added.")
    @PostMapping("school")
    public SchoolDto addSchool(@RequestBody AddSchoolDto addSchoolDto) {
        return schoolService.addSchool(addSchoolDto);
    }

    @Operation(summary = "List all schools.")
    @ApiResponse(responseCode = "200", description = "Found all schools.")
    @GetMapping("schools")
    public List<SchoolDto> getSchools() {
        return schoolService.getAll();
    }

    @Operation(summary = "List all classes in school.")
    @ApiResponse(responseCode = "200", description = "Found all classes in school.")
    @ApiResponse(responseCode = "404", description = "School not found.")
    @GetMapping("schools/{school}/classes")
    public List<GroupDto> getClassesFromSchool(@Parameter(description = "School name") @PathVariable("school") String schoolName) {
        try {
            return schoolService.getClasses(schoolName);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
