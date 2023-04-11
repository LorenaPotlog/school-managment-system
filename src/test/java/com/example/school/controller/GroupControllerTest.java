package com.example.school.controller;

import com.example.school.SchoolApplication;
import com.example.school.dto.input.AddGroupDto;
import com.example.school.model.Group;
import com.example.school.model.School;
import com.example.school.model.Student;
import com.example.school.repository.GroupRepository;
import com.example.school.repository.SchoolRepository;
import com.example.school.repository.StudentRepository;
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

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = SchoolApplication.class)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class GroupControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private SchoolRepository schoolRepository;

    @BeforeEach
    public void setUp() {
        School school1 = School.builder().name("Petru").build();
        schoolRepository.save(school1);

        Group group1 = Group.builder().name("I-A").build();
        Group group2 = Group.builder().name("II-A").build();

        Student student1 = Student.builder().firstName("Bianca").lastName("Ionescu").group(group1).build();
        Student student2 = Student.builder().firstName("Ioana").lastName("Popescu").group(group1).build();

        group1.setStudents(Arrays.asList(student1, student2));

        groupRepository.save(group1);
        groupRepository.save(group2);
        studentRepository.save(student1);
        studentRepository.save(student2);
    }

    @AfterEach
    public void tearDown() {
        studentRepository.deleteAll();
        groupRepository.deleteAll();
    }

    @Test
    public void shouldAddNewGroup() throws Exception {
        AddGroupDto addGroupDto = AddGroupDto.builder()
                .groupName("I-A")
                .schoolName("Petru")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        mvc.perform(post("/class").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(addGroupDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("I-A"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.school.name").value("Petru"));
    }

    @Test
    public void shouldReturnAllStudentsInClass() throws Exception {
        mvc.perform(get("/classes/I-A/students"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].firstName").value("Bianca"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].firstName").value("Ioana"));
    }

    @Test
    public void shouldReturnEmptyListWhenNoStudentsInClass() throws Exception {
        mvc.perform(get("/classes/II-A/students"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    @Test
    public void shouldDeleteStudentFromClass() throws Exception {
        mvc.perform(delete("/classes/I-A/students/2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("Student was successfully deleted."));
    }

    @Test
    public void shouldReturnHttpErrorWhenStudentNotFound() throws Exception {
        mvc.perform(delete("/classes/I-A/students/3"))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("Student not found"));
    }
}

