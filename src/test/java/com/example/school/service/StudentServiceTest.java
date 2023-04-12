package com.example.school.service;

import com.example.school.dto.input.AddStudentDto;
import com.example.school.dto.input.TransferStudentDto;
import com.example.school.model.Group;
import com.example.school.model.School;
import com.example.school.model.Student;
import com.example.school.repository.GroupRepository;
import com.example.school.repository.SchoolRepository;
import com.example.school.repository.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private GroupRepository groupRepository;
    @Mock
    private SchoolRepository schoolRepository;
    @InjectMocks
    private StudentService studentService = new StudentService();

    private Student student1;
    private Student student2;
    private Student student3;

    private Group group;
    private School school;

    private TransferStudentDto transferStudentDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        student1 = Student.builder().id(1L).firstName("Bianca").lastName("Ionescu").build();
        student2 = Student.builder().id(2L).firstName("Ioana").lastName("Popescu").build();
        student3 = Student.builder().id(3L).firstName("Mihai").build();

        group = Group.builder().id(1L).name("I-A").build();
        group.setStudents(List.of(student1, student2));

        school = School.builder().id(1L).name("Sava").build();

        transferStudentDto = TransferStudentDto.builder().studentId(1L).schoolId(1L).groupName("I-A").build();
    }

    @Test
    void shouldReturnAllStudents() {
        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        when(studentRepository.findAll()).thenReturn(students);

        assertFalse(studentService.getAll().isEmpty());
        assertEquals(2, studentService.getAll().size());
        assertEquals("Bianca", studentService.getAll().get(0).getFirstName());
    }

    @Test
    void shouldAddNewStudent() {
        AddStudentDto addStudentDto = AddStudentDto.builder()
                .firstName("Bianca")
                .lastName("Ionescu")
                .groupName("I-A")
                .schoolName("Sava")
                .build();

        when(groupRepository.findByName("I-A")).thenReturn(Optional.of(group));
        when(schoolRepository.findByName("Sava")).thenReturn(Optional.of(school));

        studentService.add(addStudentDto);

        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentArgumentCaptor.capture());
        Student capturedStudent = studentArgumentCaptor.getValue();

        assertEquals("Bianca", capturedStudent.getFirstName());
    }

    @Test
    void shouldReturnErrorWhenGroupNotFound() {
        AddStudentDto addStudentDto = AddStudentDto.builder()
                .firstName("Bianca")
                .lastName("Ionescu")
                .groupName("I-A")
                .schoolName("Sava")
                .build();

        when(schoolRepository.findByName("Sava")).thenReturn(Optional.of(school));

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> studentService.add(addStudentDto));
        assertEquals("Group not found.", exception.getMessage());
    }

    @Test
    public void shouldDeleteStudentFromGivenClass() {
        when(groupRepository.findByName("I-A")).thenReturn(Optional.of(group));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student1));
        when(studentRepository.findById(2L)).thenReturn(Optional.of(student2));

        studentService.delete("I-A", 1L);
    }

    @Test
    public void shouldReturnErrorWhenStudentNotFoundInGroup() {
        when(groupRepository.findByName("I-A")).thenReturn(Optional.of(group));
        when(studentRepository.findById(3L)).thenReturn(Optional.of(student3));

        Assertions.assertThrows(IllegalArgumentException.class, () -> studentService.delete("I-A", 3L));
    }

    @Test
    public void shouldReturnErrorWhenStudentNotFound() {
        when(groupRepository.findByName("I-A")).thenReturn(Optional.of(group));

        Assertions.assertThrows(IllegalArgumentException.class, () -> studentService.delete("I-A", 1L));
    }


    @Test
    void shouldChangeGroupInStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student2));
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));

        studentService.changeGroup(1L, 1L);

        Mockito.verify(studentRepository).save(student2);
    }

    @Test
    void shouldThrowExceptionWhenGroupNotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student2));

        Exception exception = assertThrows(NotFoundException.class, () -> {
            studentService.changeGroup(1L, 2L);
        });

        assertEquals(exception.getMessage(), "Group not found");
        assertNull(student2.getGroup());
    }

    @Test
    void shouldThrowExceptionWhenStudentNotFound() {
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));

        Exception exception = assertThrows(NotFoundException.class, () -> {
            studentService.changeGroup(2L, 1L);
        });

        assertEquals(exception.getMessage(), "Student not found");
    }

    @Test
    void shouldChangeSchoolInStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student2));
        when(schoolRepository.findById(1L)).thenReturn(Optional.of(school));

        studentService.changeSchool(1L, 1L);

        Mockito.verify(studentRepository).save(student2);
    }

    @Test
    void shouldThrowExceptionWhenSchoolNotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student2));

        Exception exception = assertThrows(NotFoundException.class, () -> {
            studentService.changeSchool(1L, 2L);
        });
        assertEquals(exception.getMessage(), "School not found");
    }

    @Test
    void shouldChangeSchoolAndGroupInStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student2));
        when(schoolRepository.findById(1L)).thenReturn(Optional.of(school));
        when(groupRepository.findByName("I-A")).thenReturn(Optional.of(group));
        group.setSchool(school);

        studentService.changeSchoolAndGroup(transferStudentDto);

        Mockito.verify(studentRepository).save(student2);
    }

    @Test
    void shouldChangeSchoolAndSetGroupToNull() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student2));
        when(schoolRepository.findById(1L)).thenReturn(Optional.of(school));
        when(groupRepository.findByName("I-A")).thenReturn(Optional.of(group));

        studentService.changeSchoolAndGroup(transferStudentDto);

        assertNull(student2.getGroup());
    }
}