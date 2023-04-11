package com.example.school.service;

import com.example.school.dto.input.AddTeacherDto;
import com.example.school.model.Group;
import com.example.school.model.School;
import com.example.school.model.Teacher;
import com.example.school.repository.GroupRepository;
import com.example.school.repository.SchoolRepository;
import com.example.school.repository.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class TeacherServiceTest {

    @InjectMocks
    TeacherService teacherService = new TeacherService();
    @Mock
    private TeacherRepository teacherRepository;
    @Mock
    private SchoolRepository schoolRepository;
    @Mock
    private GroupRepository groupRepository;

    @Test
    void shouldReturnAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(Teacher.builder().firstName("Andreea").build());
        teachers.add(Teacher.builder().firstName("Ionel").build());
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

    @Test
    void shouldAddNewTeacher() {
        AddTeacherDto addTeacherDto = AddTeacherDto.builder()
                .firstName("Bianca")
                .lastName("Popescu")
                .groupNames(List.of("1", "2"))
                .schoolNames(List.of("1", "2"))
                .build();
        Group group1 = Group.builder().name("1").build();
        School school1 = School.builder().name("1").build();
        when(groupRepository.findByName("1")).thenReturn(Optional.of(group1));
        when(schoolRepository.findByName("1")).thenReturn(Optional.of(school1));

        teacherService.addTeacher(addTeacherDto);

        ArgumentCaptor<Teacher> teacherArgumentCaptor = ArgumentCaptor.forClass(Teacher.class);
        verify(teacherRepository).save(teacherArgumentCaptor.capture());
        Teacher capturedTeacher = teacherArgumentCaptor.getValue();

        assertEquals("Bianca", capturedTeacher.getFirstName());
        assertEquals("1", capturedTeacher.getGroups().get(0).getName());
    }
}
