package com.example.school.service;

import com.example.school.dto.GroupDto;
import com.example.school.dto.StudentDto;
import com.example.school.dto.input.AddGroupDto;
import com.example.school.model.Group;
import com.example.school.model.School;
import com.example.school.model.Student;
import com.example.school.repository.GroupRepository;
import com.example.school.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private SchoolRepository schoolRepository;

    public List<GroupDto> getAll() {
        List<GroupDto> groups = new ArrayList<>();
        for (Group group : groupRepository.findAll()) {
            groups.add(GroupDto.toGroupDto(group));
        }
        return groups;
    }

    public GroupDto add(AddGroupDto addGroupDto) {
        Group newGroup = Group.builder()
                .name(addGroupDto.getGroupName())
                .build();

        Optional<School> school = schoolRepository.findByName(addGroupDto.getSchoolName());
        if (school.isEmpty()) {
            throw new NotFoundException("School not found.");
        } else newGroup.setSchool(school.get());

        return GroupDto.toGroupDto(groupRepository.save(newGroup));
    }

    public List<StudentDto> getStudents(String groupName) {
        List<StudentDto> students = new ArrayList<>();
        Optional<Group> group = groupRepository.findByName(groupName);
        if (group.isEmpty()) {
            throw new NotFoundException("Group not found.");
        }
        for (Student student : group.get().getStudents()) {
            students.add(StudentDto.toStudentDto(student));
        }
        return students;
    }
}
