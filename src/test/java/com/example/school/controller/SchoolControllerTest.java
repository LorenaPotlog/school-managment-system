package com.example.school.controller;

import com.example.school.model.Group;
import com.example.school.model.School;
import com.example.school.repository.SchoolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class SchoolControllerTest {
    @InjectMocks
    SchoolController schoolController = new SchoolController();
    @Mock
    private SchoolRepository schoolRepository;
    private School school1;
    private School school2;


    private List<Group> groups;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        school1 = School.builder().schoolName("A").build();
        school2 = School.builder().schoolName("B").build();

        Group group1 = Group.builder().groupName("I-A").school(school1).build();
        Group group2 = Group.builder().groupName("II-A").school(school1).build();

        groups = new ArrayList<>();
        groups.add(group1);
        groups.add(group2);

        school1.setGroups(groups);
    }

    @Test
    void shouldReturnAllSchools() {

        List<School> schools = new ArrayList<>();
        schools.add(school1);
        schools.add(school2);

        when(schoolRepository.findAll()).thenReturn(schools);
        assertEquals(2, schoolController.getSchools().size());
    }

    @Test
    void shouldReturnAllClassesFromGivenSchool() {
        when(schoolRepository.findBySchoolName("A")).thenReturn(Optional.ofNullable(school1));
        List<Group> actualGroups = schoolController.getClassesFromSchool("A");
        assertEquals(actualGroups, groups);
    }

}


