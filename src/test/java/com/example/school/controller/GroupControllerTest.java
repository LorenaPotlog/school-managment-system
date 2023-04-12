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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    void shouldReturnAllGroups() throws Exception {
        mvc.perform(get("/groups"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value("I-A"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].name").value("II-A"));
    }

    @Test
    public void shouldAddNewGroup() throws Exception {
        AddGroupDto addGroupDto = AddGroupDto.builder()
                .groupName("I-A")
                .schoolName("Petru")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        mvc.perform(post("/group").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(addGroupDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.name").value("I-A"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.school.name").value("Petru"));
    }

    @Test
    public void shouldReturnAllStudentsInGroup() throws Exception {
        mvc.perform(get("/groups/I-A/students"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.[0].firstName").value("Bianca"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.[1].firstName").value("Ioana"));
    }
}

