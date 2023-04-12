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

    public List<SchoolDto> getAll() {
        List<SchoolDto> schools = new ArrayList<>();
        for (School school : schoolRepository.findAll()) {
            schools.add(SchoolDto.toSchoolDto(school));
        }
        return schools;
    }

    public SchoolDto add(AddSchoolDto addSchoolDto) {
        School newSchool = School.builder()
                .name(addSchoolDto.getSchoolName())
                .build();

        for (int i = 0; i < addSchoolDto.getTeacherIds().size(); i++) {
            Optional<Teacher> teacher = teacherRepository.findById(addSchoolDto.getTeacherIds().get(i));
            if (teacher.isEmpty()) {
                throw new NotFoundException("Teacher not found.");
            }
            newSchool.addTeacher(teacher.get());
        }
        return SchoolDto.toSchoolDto(schoolRepository.save(newSchool));
    }

    public List<GroupDto> getGroups(String schoolName) {
        if (schoolRepository.findByName(schoolName).isPresent()) {
            List<Group> groups = schoolRepository.findByName(schoolName).get().getGroups();
            return groups.stream()
                    .map(GroupDto::toGroupDto)
                    .collect(Collectors.toList());
        } else throw new NotFoundException("School not found.");
    }
}
