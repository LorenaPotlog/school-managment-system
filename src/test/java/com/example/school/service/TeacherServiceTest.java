package com.example.school.service;

import com.example.school.model.Teacher;
import com.example.school.repository.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class TeacherServiceTest {

    @InjectMocks
    TeacherService teacherService = new TeacherService();
    @Mock
    private TeacherRepository teacherRepository;

    @Test
    void shouldReturnAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(Teacher.builder()
                .firstName("Andreea")
                .build());
        teachers.add(Teacher.builder()
                .firstName("Ionel")
                .build());

        when(teacherRepository.findAll()).thenReturn(teachers);

        assertFalse(teacherService.getAll().isEmpty());
        assertEquals(2, teacherService.getAll().size());
        assertEquals("Andreea", teacherService.getAll().get(0).getFirstName());

    }

    @Test
    void shouldReturnNoDataWhenNoTeachers() {
        when(teacherRepository.findAll()).thenReturn(new ArrayList<>());

        assertEquals(0, teacherService.getAll().size());
    }

}
