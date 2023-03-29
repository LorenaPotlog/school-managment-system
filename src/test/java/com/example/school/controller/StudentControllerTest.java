package com.example.school.controller;

import com.example.school.SchoolApplication;
import com.example.school.model.Student;
import com.example.school.repository.StudentRepository;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = SchoolApplication.class)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    public void setUp() {

        Student student1 = Student.builder().firstName("Bianca").lastName("Ionescu").build();
        Student student2 = Student.builder().firstName("Ioana").build();

        studentRepository.saveAll(Arrays.asList(student1, student2));
    }

    @After
    public void tearDown() {

        studentRepository.deleteAll();
    }

    @Test
    void shouldReturnAllStudents() throws Exception {
        mvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].firstName").value("Bianca"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].firstName").value("Ioana"));
    }
}
