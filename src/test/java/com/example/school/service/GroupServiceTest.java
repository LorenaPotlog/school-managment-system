package com.example.school.service;

import com.example.school.dto.input.AddGroupDto;
import com.example.school.model.Group;
import com.example.school.model.School;
import com.example.school.model.Student;
import com.example.school.repository.GroupRepository;
import com.example.school.repository.SchoolRepository;
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
class GroupServiceTest {
    @InjectMocks
    GroupService groupService = new GroupService();
    @Mock
    GroupRepository groupRepository;
    @Mock
    SchoolRepository schoolRepository;
    private Group group1;
    private Group group2;
    private School school;

    private List<Group> groups;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        group1 = Group.builder().id(1L).name("I-A").build();
        group2 = Group.builder().id(2L).name("II-A").build();

        groups = new ArrayList<>();
        groups.add(group1);
        groups.add(group2);

        Student student1 = Student.builder().id(1L).firstName("Ioana").build();
        Student student2 = Student.builder().id(2L).firstName("Livia").build();
        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);

        group1.setStudents(students);

        school = School.builder().name("Petru").build();
    }

    @Test
    void shouldReturnAllGroups() {
        when(groupRepository.findAll()).thenReturn(groups);

        assertEquals(2, groupService.getAll().size());
    }

    @Test
    void shouldAddNewGroup() {
        AddGroupDto addGroupDto = AddGroupDto.builder()
                .groupName("I-A")
                .schoolName("Petru")
                .build();
        when(schoolRepository.findByName("Petru")).thenReturn(Optional.of(school));

        groupService.add(addGroupDto);

        ArgumentCaptor<Group> groupArgumentCaptor = ArgumentCaptor.forClass(Group.class);
        verify(groupRepository).save(groupArgumentCaptor.capture());
        Group capturedGroup = groupArgumentCaptor.getValue();

        assertEquals("I-A", capturedGroup.getName());
    }

    @Test
    void shouldReturnErrorWhenSchoolNotFound() {
        AddGroupDto addGroupDto = AddGroupDto.builder()
                .groupName("I-A")
                .schoolName("Petru")
                .build();

        assertThrows(NotFoundException.class,
                () -> groupService.add(addGroupDto));
    }

    @Test
    public void shouldReturnStudentsWithinGivenGroup() {
        when(groupRepository.findByName("I-A")).thenReturn(Optional.of(group1));

        Assertions.assertFalse(groupService.getStudents("I-A").isEmpty());
        Assertions.assertEquals(2, groupService.getStudents("I-A").size());
        Assertions.assertEquals("Ioana", groupService.getStudents("I-A").get(0).getFirstName());
    }

    @Test
    void shouldReturnErrorWhenGroupNotFound() {
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> groupService.getStudents("III-A"));
        assertEquals("Group not found.", exception.getMessage());
    }
}