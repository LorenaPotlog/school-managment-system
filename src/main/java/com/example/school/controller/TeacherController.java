package com.example.school.controller;

import com.example.school.dto.TeacherDto;
import com.example.school.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    /**
     * returns all teachers
     **/
    @GetMapping("teachers")
    public List<TeacherDto> getTeachers() {
        return teacherService.getAll();
    }

}
