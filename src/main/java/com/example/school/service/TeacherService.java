package com.example.school.service;

import com.example.school.dto.TeacherDto;
import com.example.school.dto.input.AddTeacherDto;
import com.example.school.model.Group;
import com.example.school.model.School;
import com.example.school.model.Teacher;
import com.example.school.repository.GroupRepository;
import com.example.school.repository.SchoolRepository;
import com.example.school.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private SchoolRepository schoolRepository;

    public List<TeacherDto> getAll() {
        List<TeacherDto> teachers = new ArrayList<>();
        for (Teacher teacher : teacherRepository.findAll()) {
            teachers.add(TeacherDto.toTeacherDto(teacher));
        }
        return teachers;
    }

    public TeacherDto addTeacher(AddTeacherDto addTeacherDto) {
        Teacher newTeacher = Teacher.builder()
                .firstName(addTeacherDto.getFirstName())
                .lastName(addTeacherDto.getLastName())
                .build();
        for (int i = 0; i < addTeacherDto.getGroupNames().size(); i++) {
            Optional<Group> group = groupRepository.findByName(addTeacherDto.getGroupNames().get(i));
            group.ifPresent(value -> newTeacher.addGroup(group.get()));
        }
        for (int i = 0; i < addTeacherDto.getGroupNames().size(); i++) {
            Optional<School> school = schoolRepository.findByName(addTeacherDto.getSchoolNames().get(i));
            school.ifPresent(value -> newTeacher.addSchool(school.get()));
        }
        return TeacherDto.toTeacherDto(teacherRepository.save(newTeacher));
    }
}

