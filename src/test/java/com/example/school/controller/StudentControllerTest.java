package com.example.school.controller;

import com.example.school.SchoolApplication;
import com.example.school.dto.input.AddStudentDto;
import com.example.school.dto.input.TransferStudentDto;
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

import java.util.Collections;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = SchoolApplication.class)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class StudentControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private SchoolRepository schoolRepository;

    @BeforeEach
    public void setUp() {
        School school1 = School.builder().name("Sava").build();
        School school2 = School.builder().name("Petru").build();

        Group group1 = Group.builder().name("I-A").build();
        Group group2 = Group.builder().name("II-A").school(school2).build();

        school1.setGroups(Collections.singletonList(group1));

        schoolRepository.save(school1);
        schoolRepository.save(school2);
        groupRepository.save(group1);
        groupRepository.save(group2);

        Student student1 = Student.builder().firstName("Bianca").lastName("Ionescu").group(group1).school(school1).build();
        Student student2 = Student.builder().firstName("Ioana").lastName("Popescu").group(group1).school(school1).build();

        studentRepository.save(student1);
        studentRepository.save(student2);
    }

    @AfterEach
    public void tearDown() {
        studentRepository.deleteAll();
        groupRepository.deleteAll();
        schoolRepository.deleteAll();
    }

    @Test
    void shouldReturnAllStudents() throws Exception {
        mvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].firstName").value("Bianca"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].firstName").value("Ioana"));
    }

    @Test
    void shouldAddNewStudent() throws Exception {
        AddStudentDto addStudentDto = AddStudentDto.builder()
                .firstName("Bianca")
                .lastName("Ionescu")
                .groupName("I-A")
                .schoolName("Sava")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        mvc.perform(post("/student").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(addStudentDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.firstName").value("Bianca"));
    }

    @Test
    public void shouldDeleteStudentFromGroup() throws Exception {
        mvc.perform(delete("/student/2/group/I-A"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("Student was successfully deleted."));
    }

    @Test
    public void shouldReturnHttpErrorWhenStudentNotFound() throws Exception {
        mvc.perform(delete("/student/3/group/I-A"))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("Student not found"));
    }

    @Test
    void shouldChangeGroup() throws Exception {
        mvc.perform(patch("/student/2/group/2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.group.name").value("II-A"));
    }

    @Test
    void shouldChangeSchool() throws Exception {
        mvc.perform(patch("/student/1/school/2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.school.name").value("Petru"));
    }

    @Test
    void shouldChangeSchoolAndGroup() throws Exception {
        TransferStudentDto transferStudentDto = TransferStudentDto.builder()
                .studentId(2L)
                .schoolId(2L)
                .groupName("II-A")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        mvc.perform(patch("/student").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(transferStudentDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.group.name").value("II-A"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.school.name").value("Petru"));
    }
}

