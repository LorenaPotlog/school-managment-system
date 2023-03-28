package com.example.school.controller;

import com.example.school.model.Group;
import com.example.school.model.School;
import com.example.school.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SchoolController {
    @Autowired
    private SchoolRepository schoolRepository;

    /**
     * returns all schools
     **/
    @GetMapping("schools")
    public List<School> getSchools() {
        List<School> schools = new ArrayList<>();
        for (School school : schoolRepository.findAll()) {
            schools.add(school);
        }
        return schools;
    }

    /**
     * returnes all classes within a given school
     **/
    @GetMapping("schools/{schoolName}/classes")
    public List<Group> getClassesFromSchool(@PathVariable String schoolName) {
        return new ArrayList<>(schoolRepository.findBySchoolName(schoolName).get().getGroups());
    }
}
