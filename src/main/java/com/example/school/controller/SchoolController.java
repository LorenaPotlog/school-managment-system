package com.example.school.controller;

import com.example.school.dto.GroupDto;
import com.example.school.dto.SchoolDto;
import com.example.school.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SchoolController {
    @Autowired
    private SchoolService schoolService;

    /**
     * returns all schools
     **/
    @GetMapping("schools")
    public List<SchoolDto> getSchools() {
        return schoolService.getAll();
    }

    /**
     * returnes all classes within a given school
     **/
    @GetMapping("schools/{schoolName}/classes")
    public List<GroupDto> getClassesFromSchool(@PathVariable String schoolName) {
        return schoolService.getClasses(schoolName);
    }
}
