package com.example.school.service;

import com.example.school.dto.GroupDto;
import com.example.school.dto.SchoolDto;
import com.example.school.model.Group;
import com.example.school.model.School;
import com.example.school.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SchoolService {

    @Autowired
    private SchoolRepository schoolRepository;

    public List<SchoolDto> getAll() {
        List<SchoolDto> schools = new ArrayList<>();

        for (School school : schoolRepository.findAll()) {
            schools.add(SchoolDto.toSchoolDto(school));
        }
        return schools;
    }

    public List<GroupDto> getClasses(String schoolName) {
        List<Group> classes = schoolRepository.findBySchoolName(schoolName).get().getGroups();

        return classes.stream()
                .map(GroupDto::toGroupDto)
                .collect(Collectors.toList());
    }

}
