package com.example.school.controller;

import com.example.school.model.Group;
import com.example.school.model.Student;
import com.example.school.repository.GroupRepository;
import com.example.school.repository.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class GroupControllerTest {

    @InjectMocks
    GroupController groupController = new GroupController();

    @Mock
    GroupRepository groupRepository;

    @Mock
    StudentRepository studentRepository;

    private Group group;

    private Student student1;
    private Student student2;
    private Student student3;


    private List<Student> students;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        group = Group.builder().groupId(1L).groupName("I-A").build();

        student1 = Student.builder().studentId(1L).firstName("Ioana").build();
        student2 = Student.builder().studentId(2L).firstName("Livia").build();
        student3 = Student.builder().studentId(3L).firstName("Mihai").build();


        students = new ArrayList<>();
        students.add(student1);
        students.add(student2);

        group.setStudents(students);

    }

    @Test
    public void shouldReturnStudentsWithinGivenClass() {
        when(groupRepository.findByGroupName("I-A")).thenReturn(Optional.ofNullable(group));
        Assertions.assertEquals(students, groupController.getStudentsFromClass("I-A"));

    }

    @Test
    public void shouldDeleteStudentFromGivenClass() {
        when(groupRepository.findByGroupName("I-A")).thenReturn(Optional.ofNullable(group));
        when(studentRepository.findById(1L)).thenReturn(Optional.ofNullable(student1));
        when(studentRepository.findById(2L)).thenReturn(Optional.ofNullable(student2));
        ResponseEntity<String> result = groupController.deleteStudent("I-A", 1L);
        Assertions.assertEquals("Successfully deleted student.", result.getBody());
    }

    @Test
    public void shouldReturnErrorWhenStudentNotFoundInClass() {
        when(groupRepository.findByGroupName("I-A")).thenReturn(Optional.ofNullable(group));
        when(studentRepository.findById(3L)).thenReturn(Optional.ofNullable(student3));
        ResponseEntity<String> result = groupController.deleteStudent("I-A", 3L);
        Assertions.assertEquals("Student not found in class.", result.getBody());
    }

    @Test
    public void shouldReturnErrorWhenStudentNotFound() {
        when(groupRepository.findByGroupName("I-A")).thenReturn(Optional.ofNullable(group));
        ResponseEntity<String> result = groupController.deleteStudent("I-A", 3L);
        Assertions.assertEquals("Student not found.", result.getBody());
    }

    @Test
    public void shouldReturnErrorWhenClassNotFoundInClass() {
        when(studentRepository.findById(3L)).thenReturn(Optional.ofNullable(student3));
        ResponseEntity<String> result = groupController.deleteStudent("I-A", 3L);
        Assertions.assertEquals("Class not found.", result.getBody());
    }


}