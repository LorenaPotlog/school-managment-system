package com.example.school.controller;

import com.example.school.model.Group;
import com.example.school.model.School;
import com.example.school.model.Student;
import com.example.school.repository.GroupRepository;
import com.example.school.repository.SchoolRepository;
import com.example.school.repository.StudentRepository;
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
class StudentControllerTest {

    Student student1;
    Student student2;
    Group group;

    School school;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private GroupRepository groupRepository;
    @Mock
    private SchoolRepository schoolRepository;
    @InjectMocks
    private StudentController studentController = new StudentController();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        student1 = Student.builder().firstName("Bianca").lastName("Ionescu").build();
        student2 = Student.builder().studentId(1L).firstName("Ioana").build();

        group = Group.builder().groupId(1L).groupName("I-A").build();

        school = School.builder().schoolId(1L).build();
    }

    @Test
    void shouldReturnAllStudents() {

        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);

        when(studentRepository.findAll()).thenReturn(students);
        assertEquals(2, studentController.getStudents().size());
        verify(studentRepository).findAll();
    }

    @Test
    void shouldAddNewStudent() {
        studentController.addNewStudent("Bianca", "Ionescu");

        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentArgumentCaptor.capture());
        Student capturedStudent = studentArgumentCaptor.getValue();
        assertEquals(student1, capturedStudent);

    }

    @Test
    void shouldChangeClassInStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.ofNullable(student2));
        when(groupRepository.findById(1L)).thenReturn(Optional.ofNullable(group));
        studentController.updateStudentByClass(1L, 1L);
        Mockito.verify(studentRepository).save(student2);
    }

    @Test
    void shouldThrowExceptionWhenGroupNotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.ofNullable(student2));
        Exception exception = assertThrows(NotFoundException.class, () -> {
            studentController.updateStudentByClass(1L, 2L);
        });
        assertEquals(exception.getMessage(), "Group not found");
        assertNull(student2.getGroup());
    }

    @Test
    void shouldThrowExceptionWhenStudentNotFound() {
        when(groupRepository.findById(1L)).thenReturn(Optional.ofNullable(group));
        Exception exception = assertThrows(NotFoundException.class, () -> {
            studentController.updateStudentByClass(2L, 1L);
        });
        assertEquals(exception.getMessage(), "Student not found");
    }

    @Test
    void shouldChangeSchoolInStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.ofNullable(student2));
        when(schoolRepository.findById(1L)).thenReturn(Optional.ofNullable(school));
        studentController.transferStudent(1L, 1L);
        Mockito.verify(studentRepository).save(student2);
    }

    @Test
    void shouldThrowExceptionWhenSchoolNotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.ofNullable(student2));
        Exception exception = assertThrows(NotFoundException.class, () -> {
            studentController.transferStudent(1L, 2L);
        });
        assertEquals(exception.getMessage(), "School not found");
    }

    @Test
    void shouldChangeSchoolAndGroupInStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.ofNullable(student2));
        when(schoolRepository.findById(1L)).thenReturn(Optional.ofNullable(school));
        when(groupRepository.findByGroupName("I-A")).thenReturn(Optional.ofNullable(group));
        group.setSchool(school);
        studentController.transferStudentAndEnroll(1L, 1L, "I-A");
        Mockito.verify(studentRepository).save(student2);

    }

    @Test
    void shouldChangeSchoolAndSetGroupToNull() {
        when(studentRepository.findById(1L)).thenReturn(Optional.ofNullable(student2));
        when(schoolRepository.findById(1L)).thenReturn(Optional.ofNullable(school));
        when(groupRepository.findByGroupName("I-A")).thenReturn(Optional.ofNullable(group));
        studentController.transferStudentAndEnroll(1L, 1L, "I-A");
        assertNull(student2.getGroup());

    }

}
