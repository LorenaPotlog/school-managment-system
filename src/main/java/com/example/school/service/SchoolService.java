package com.example.school.service;

import com.example.school.dto.GroupDto;
import com.example.school.dto.SchoolDto;
import com.example.school.dto.input.AddSchoolDto;
import com.example.school.model.Group;
import com.example.school.model.School;
import com.example.school.model.Teacher;
import com.example.school.repository.SchoolRepository;
import com.example.school.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SchoolService {

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    public SchoolDto addSchool(AddSchoolDto addSchoolDto) {
        School newSchool = School.builder()
                .schoolName(addSchoolDto.getSchoolName())
                .build();

        for (int i = 0; i < addSchoolDto.getTeacherIds().size(); i++) {
            Optional<Teacher> teacher = teacherRepository.findByTeacherId(addSchoolDto.getTeacherIds().get(i));
            teacher.ifPresent(value -> newSchool.addTeacher(teacher.get()));
        }

        return SchoolDto.toSchoolDto(schoolRepository.save(newSchool));
    }

    public List<SchoolDto> getAll() {
        List<SchoolDto> schools = new ArrayList<>();

        for (School school : schoolRepository.findAll()) {
            schools.add(SchoolDto.toSchoolDto(school));
        }
        return schools;
    }

    public List<GroupDto> getClasses(String schoolName) {

        if (schoolRepository.findBySchoolName(schoolName).isPresent()) {
            List<Group> classes = schoolRepository.findBySchoolName(schoolName).get().getGroups();
            return classes.stream()
                    .map(GroupDto::toGroupDto)
                    .collect(Collectors.toList());

        } else throw new NotFoundException("School not found.");
    }

}
