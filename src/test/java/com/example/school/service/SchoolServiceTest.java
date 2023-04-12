package com.example.school.service;

import com.example.school.dto.input.AddSchoolDto;
import com.example.school.model.Group;
import com.example.school.model.School;
import com.example.school.model.Teacher;
import com.example.school.repository.SchoolRepository;
import com.example.school.repository.TeacherRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class SchoolServiceTest {
    @InjectMocks
    SchoolService schoolService = new SchoolService();
    @Mock
    private SchoolRepository schoolRepository;
    @Mock
    TeacherRepository teacherRepository;

    private School school1;
    private School school2;
    private Teacher teacher1;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        school1 = School.builder().name("A").build();
        school2 = School.builder().name("B").build();

        Group group1 = Group.builder().name("I-A").school(school1).build();
        Group group2 = Group.builder().name("II-A").school(school1).build();
        List<Group> groups = new ArrayList<>();
        groups.add(group1);
        groups.add(group2);

        school1.setGroups(groups);

        teacher1 = Teacher.builder().firstName("Andrei").build();
    }

    @Test
    void shouldAddNewSchool() {
        AddSchoolDto addSchoolDto = AddSchoolDto.builder()
                .schoolName("Sava")
                .teacherIds(List.of(1L))
                .build();
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher1));

        schoolService.add(addSchoolDto);

        ArgumentCaptor<School> schoolArgumentCaptor = ArgumentCaptor.forClass(School.class);
        verify(schoolRepository).save(schoolArgumentCaptor.capture());
        School capturedSchool = schoolArgumentCaptor.getValue();

        assertEquals("Sava", capturedSchool.getName());
        assertEquals("Andrei", capturedSchool.getTeachers().get(0).getFirstName());

    }

    @Test
    void shouldReturnErrorWhenTeacherNotFound() {
        AddSchoolDto addSchoolDto = AddSchoolDto.builder()
                .schoolName("Sava")
                .teacherIds(List.of(1L))
                .build();

        assertThrows(NotFoundException.class,
                () -> schoolService.add(addSchoolDto));
    }

    @Test
    void shouldReturnAllSchools() {
        List<School> schools = new ArrayList<>();
        schools.add(school1);
        schools.add(school2);
        when(schoolRepository.findAll()).thenReturn(schools);

        assertEquals(2, schoolService.getAll().size());
        assertEquals("A", schoolService.getAll().get(0).getName());
    }

    @Test
    void shouldReturnAllClassesFromGivenSchool() {
        when(schoolRepository.findByName("A")).thenReturn(Optional.of(school1));

        assertEquals(2, schoolService.getGroups("A").size());
        assertEquals("II-A", schoolService.getGroups("A").get(1).getName());
    }

    @Test
    void shouldReturnErrorWhenSchoolNotFound() {
        Assertions.assertThrows(NotFoundException.class,
                () -> schoolService.getGroups("Sava"));
    }
}