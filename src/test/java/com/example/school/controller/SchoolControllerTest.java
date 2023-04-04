package com.example.school.controller;

import com.example.school.SchoolApplication;
import com.example.school.dto.input.AddSchoolDto;
import com.example.school.model.Group;
import com.example.school.model.School;
import com.example.school.model.Teacher;
import com.example.school.repository.SchoolRepository;
import com.example.school.repository.TeacherRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = SchoolApplication.class)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class SchoolControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @BeforeEach
    public void setUp() {

        Teacher teacher1 = Teacher.builder().firstName("Ioana").build();

        teacherRepository.save(teacher1);

        School school1 = School.builder().schoolName("A").build();
        School school2 = School.builder().schoolName("B").build();

        Group group1 = Group.builder().groupName("I-A").build();
        Group group2 = Group.builder().groupName("II-A").build();
        Group group3 = Group.builder().groupName("III-A").build();

        school1.addGroup(group1);
        school1.addGroup(group2);
        school1.addGroup(group3);

        schoolRepository.save(school1);
        schoolRepository.save(school2);
    }

    @AfterEach
    public void tearDown() {
        schoolRepository.deleteAll();
    }

    @Test
    void shouldAddNewSchool() throws Exception {
        AddSchoolDto addSchoolDto = AddSchoolDto.builder()
                .schoolName("Sava")
                .teacherIds(List.of(1L, 2L, 3L))
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        mvc.perform(post("/school").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(addSchoolDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.teachers.[0].firstName").value("Ioana"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.schoolName").value("Sava"));
    }

    @Test
    void shouldReturnAllSchools() throws Exception {
        mvc.perform(get("/schools"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].schoolName").value("A"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].schoolName").value("B"));
    }

    @Test
    void shouldReturnAllClassesInSchool() throws Exception {
        mvc.perform(get("/schools/A/classes"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].groupName").value("I-A"));

    }

    @Test
    void shouldReturnAllClassesInSchool2() throws Exception {
        mvc.perform(get("/schools/B/classes"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(0)));
    }

}


