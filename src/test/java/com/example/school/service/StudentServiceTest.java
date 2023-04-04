package com.example.school.service;

import com.example.school.dto.input.AddStudentDto;
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
    private Group group;
    private School school;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        student1 = Student.builder().studentId(3L).firstName("Bianca").lastName("Ionescu").build();
        student2 = Student.builder().studentId(1L).firstName("Ioana").lastName("Popescu").build();

        group = Group.builder().groupId(1L).groupName("I-A").build();

        school = School.builder().schoolId(1L).build();
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
                .build();

        studentService.addStudent(addStudentDto);

        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentArgumentCaptor.capture());
        Student capturedStudent = studentArgumentCaptor.getValue();

        assertEquals("Bianca", capturedStudent.getFirstName());

    }

    @Test
    void shouldChangeClassInStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student2));
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));

        studentService.changeClass(1L, 1L);

        Mockito.verify(studentRepository).save(student2);
    }

    @Test
    void shouldThrowExceptionWhenGroupNotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student2));

        Exception exception = assertThrows(NotFoundException.class, () -> {
            studentService.changeClass(1L, 2L);
        });

        assertEquals(exception.getMessage(), "Class not found");
        assertNull(student2.getGroup());
    }

    @Test
    void shouldThrowExceptionWhenStudentNotFound() {
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));

        Exception exception = assertThrows(NotFoundException.class, () -> {
            studentService.changeClass(2L, 1L);
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
        when(groupRepository.findByGroupName("I-A")).thenReturn(Optional.of(group));
        group.setSchool(school);

        studentService.changeSchoolAndClass(1L, 1L, "I-A");

        Mockito.verify(studentRepository).save(student2);

    }

    @Test
    void shouldChangeSchoolAndSetGroupToNull() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student2));
        when(schoolRepository.findById(1L)).thenReturn(Optional.of(school));
        when(groupRepository.findByGroupName("I-A")).thenReturn(Optional.of(group));

        studentService.changeSchoolAndClass(1L, 1L, "I-A");

        assertNull(student2.getGroup());

    }

}