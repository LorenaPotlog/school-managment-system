package com.example.school.controller;

import com.example.school.SchoolApplication;
import com.example.school.model.Group;
import com.example.school.model.School;
import com.example.school.model.Student;
import com.example.school.repository.GroupRepository;
import com.example.school.repository.SchoolRepository;
import com.example.school.repository.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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

        School school1 = School.builder().schoolName("Sava").build();
        School school2 = School.builder().schoolName("Petru").build();

        Group group1 = Group.builder().groupName("I-A").build();
        Group group2 = Group.builder().groupName("II-A").school(school2).build();

        school1.setGroups(Collections.singletonList(group1));

        Student student1 = Student.builder().firstName("Bianca").lastName("Ionescu").group(group1).school(school1).build();
        Student student2 = Student.builder().firstName("Ioana").lastName("Popescu").group(group1).school(school1).build();

        schoolRepository.save(school1);
        schoolRepository.save(school2);
        groupRepository.save(group1);
        groupRepository.save(group2);
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
        mvc.perform(post("/newStudent?firstname=Maria&lastname=Popa"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Maria"))
                .andExpect(MockMvcResultMatchers.content().json("{\"id\":3,\"firstName\":\"Maria\",\"lastName\":\"Popa\",\"school\":null,\"group\":null}"));
    }

    @Test
    void shouldChangeClass() throws Exception {
        mvc.perform(put("/updateClass?id=2&classId=2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.group.name").value("II-A"));
    }

    @Test
    void shouldChangeSchool() throws Exception {
        mvc.perform(put("/changeSchool?id=1&schoolId=2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.school.name").value("Petru"));
    }

    @Test
    void shouldChangeSchoolAndClass() throws Exception {
        mvc.perform(put("/transferAndEnrollInClass/II-A?id=2&schoolId=2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.group.name").value("II-A"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.school.name").value("Petru"));
    }
}

