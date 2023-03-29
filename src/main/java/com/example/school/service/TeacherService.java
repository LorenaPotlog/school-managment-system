package com.example.school.service;

import com.example.school.dto.TeacherDto;
import com.example.school.model.Teacher;
import com.example.school.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    public List<TeacherDto> getAll() {
        List<TeacherDto> teachers = new ArrayList<>();
        for (Teacher teacher : teacherRepository.findAll()) {
            teachers.add(TeacherDto.ToTeacherDto(teacher));
        }
        return teachers;
    }
}

