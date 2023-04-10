package com.example.school.service;

import com.example.school.dto.input.AddGroupDto;
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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class GroupServiceTest {
    @InjectMocks
    GroupService groupService = new GroupService();
    @Mock
    GroupRepository groupRepository;
    @Mock
    StudentRepository studentRepository;
    @Mock
    SchoolRepository schoolRepository;

    private Group group;
    private Student student1;
    private Student student2;
    private Student student3;
    private School school;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        group = Group.builder().id(1L).name("I-A").build();
        student1 = Student.builder().id(1L).firstName("Ioana").build();
        student2 = Student.builder().id(2L).firstName("Livia").build();
        student3 = Student.builder().id(3L).firstName("Mihai").build();
        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        school = School.builder().name("Petru").build();
        group.setStudents(students);
    }

    @Test
    void shouldAddNewGroup() {
        AddGroupDto addGroupDto = AddGroupDto.builder()
                .groupName("I-A")
                .schoolName("Petru")
                .build();
        when(schoolRepository.findByName("Petru")).thenReturn(Optional.of(school));
        groupService.addGroup(addGroupDto);
        ArgumentCaptor<Group> groupArgumentCaptor = ArgumentCaptor.forClass(Group.class);
        verify(groupRepository).save(groupArgumentCaptor.capture());
        Group capturedGroup = groupArgumentCaptor.getValue();
        assertEquals("I-A", capturedGroup.getName());
    }

    @Test
    public void shouldReturnStudentsWithinGivenClass() {
        when(groupRepository.findByName("I-A")).thenReturn(Optional.of(group));
        Assertions.assertFalse(groupService.getStudents("I-A").isEmpty());
        Assertions.assertEquals(2, groupService.getStudents("I-A").size());
        Assertions.assertEquals("Ioana", groupService.getStudents("I-A").get(0).getFirstName());
    }
    @Test
    public void shouldDeleteStudentFromGivenClass() {
        when(groupRepository.findByName("I-A")).thenReturn(Optional.of(group));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student1));
        when(studentRepository.findById(2L)).thenReturn(Optional.of(student2));
        groupService.deleteStudent("I-A", 1L);
    }

    @Test
    public void shouldReturnErrorWhenStudentNotFoundInClass() {
        when(groupRepository.findByName("I-A")).thenReturn(Optional.of(group));
        when(studentRepository.findById(3L)).thenReturn(Optional.of(student3));
        Assertions.assertThrows(IllegalArgumentException.class, () -> groupService.deleteStudent("I-A", 3L));
    }

    @Test
    public void shouldReturnErrorWhenStudentNotFound() {
        when(groupRepository.findByName("I-A")).thenReturn(Optional.of(group));
        Assertions.assertThrows(IllegalArgumentException.class, () -> groupService.deleteStudent("I-A", 1L));
    }

    @Test
    public void shouldReturnErrorWhenClassNotFoundInClass() {
        when(studentRepository.findById(3L)).thenReturn(Optional.of(student3));
        Assertions.assertThrows(IllegalArgumentException.class, () -> groupService.deleteStudent("II-A", 3L));
    }
}