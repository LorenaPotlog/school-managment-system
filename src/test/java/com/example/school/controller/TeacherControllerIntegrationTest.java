package com.example.school.controller;

import com.example.school.SchoolApplication;
import com.example.school.model.Teacher;
import com.example.school.repository.TeacherRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = SchoolApplication.class)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class TeacherControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private TeacherRepository teacherRepository;

    @Before
    public void setUp(){

        Teacher teacher1 = Teacher.builder().firstName("Mihai").lastName("Popa").build();
        Teacher teacher2 = Teacher.builder().firstName("Maria").lastName("Iorga").build();

        teacherRepository.saveAll(Arrays.asList(teacher1,teacher2));
    }

    @After
    public void tearDown() {
        teacherRepository.deleteAll();
    }
    @Test
    public void shouldReturnAllTeachers() throws Exception {
        mvc.perform(get("/teachers"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].firstName").value("Mihai"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].firstName").value("Maria"));
    }
}
