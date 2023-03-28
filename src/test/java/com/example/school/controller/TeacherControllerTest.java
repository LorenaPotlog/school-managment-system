package com.example.school.controller;

import com.example.school.model.Teacher;
import com.example.school.repository.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class TeacherControllerTest {

    @InjectMocks
    TeacherController teacherController = new TeacherController();
    @Mock
    private TeacherRepository teacherRepository;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnAllTeachers() throws Exception {

        List<Teacher> teachers = new ArrayList<>();
        teachers.add(Teacher.builder().firstName("Andreea").build());
        teachers.add(Teacher.builder().firstName("Ionel").build());

        Mockito.when(teacherRepository.findAll()).thenReturn(teachers);
        assertEquals(2, teacherController.getTeachers().size());

        mockMvc.perform(get("/teachers")).andExpect(status().isOk());
    }

    @Test
    void shouldReturnNoDataWhenNoTeachers() throws Exception {
        Mockito.when(teacherRepository.findAll()).thenReturn(new ArrayList<>());
        assertEquals(0, teacherController.getTeachers().size());
    }

}
