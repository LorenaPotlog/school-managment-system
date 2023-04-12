package com.example.school.controller;

import com.example.school.SchoolApplication;
import com.example.school.dto.input.AddTeacherDto;
import com.example.school.model.Group;
import com.example.school.model.School;
import com.example.school.model.Teacher;
import com.example.school.repository.GroupRepository;
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

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = SchoolApplication.class)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TeacherControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private SchoolRepository schoolRepository;

    @BeforeEach
    public void setUp() {
        Teacher teacher1 = Teacher.builder().firstName("Mihai").lastName("Popa").build();
        Teacher teacher2 = Teacher.builder().firstName("Maria").lastName("Iorga").build();

        teacherRepository.saveAll(Arrays.asList(teacher1, teacher2));

        Group group1 = Group.builder().name("1").build();
        groupRepository.save(group1);

        School school1 = School.builder().name("1").build();
        schoolRepository.save(school1);
    }

    @AfterEach
    public void tearDown() {
        teacherRepository.deleteAll();
    }

    @Test
    public void shouldReturnAllTeachers() throws Exception {
        mvc.perform(get("/teachers"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].firstName").value("Mihai"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].firstName").value("Maria"));
    }

    @Test
    public void shouldAddNewTeacher() throws Exception {
        AddTeacherDto addTeacherDto = AddTeacherDto.builder()
                .firstName("Mihai")
                .lastName("Popa")
                .groupNames(List.of("1"))
                .schoolNames(List.of("1"))
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        mvc.perform(post("/teacher").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(addTeacherDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.firstName").value("Mihai"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.groups.[0].name").value("1"));
    }
}
