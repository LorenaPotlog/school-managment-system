package com.example.school.controller;

import com.example.school.model.Teacher;
import com.example.school.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TeacherController {
    @Autowired
    private TeacherRepository teacherRepository;

    /**
     * returns all teachers
     **/
    @GetMapping("teachers")
    public List<Teacher> getTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        for (Teacher teacher : teacherRepository.findAll()) {
            teachers.add(teacher);
        }
        return teachers;
    }

}
