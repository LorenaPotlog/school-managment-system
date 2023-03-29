package com.example.school.controller;

import com.example.school.SchoolApplication;
import com.example.school.model.Group;
import com.example.school.model.School;
import com.example.school.repository.SchoolRepository;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = SchoolApplication.class)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class SchoolControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private SchoolRepository schoolRepository;

    @BeforeEach
    public void setUp() {

        School school1 = School.builder().schoolName("A").build();
        School school2 = School.builder().schoolName("B").build();

        Group group1 = Group.builder().groupName("I-A").school(school1).build();
        Group group2 = Group.builder().groupName("II-A").school(school1).build();

        List<Group> groups = new ArrayList<>();
        groups.add(group1);
        groups.add(group2);

        school1.setGroups(groups);
    }

    @After
    public void tearDown() {
        schoolRepository.deleteAll();
    }

    @Test
    void shouldReturnAllSchools() {

    }

}


