package com.example.school.service;

import com.example.school.dto.GroupDto;
import com.example.school.dto.StudentDto;
import com.example.school.dto.input.AddGroupDto;
import com.example.school.model.Group;
import com.example.school.model.School;
import com.example.school.model.Student;
import com.example.school.repository.GroupRepository;
import com.example.school.repository.SchoolRepository;
import com.example.school.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private SchoolRepository schoolRepository;

    public GroupDto addGroup(AddGroupDto addGroupDto) {
        Group newGroup = Group.builder()
                .name(addGroupDto.getGroupName())
                .build();
        Optional<School> school = schoolRepository.findByName(addGroupDto.getSchoolName());
        school.ifPresent(newGroup::setSchool);
        return GroupDto.toGroupDto(groupRepository.save(newGroup));
    }

    public List<StudentDto> getStudents(String className) {
        List<StudentDto> students = new ArrayList<>();
        for (Student student : groupRepository.findByName(className).get().getStudents()) {
            students.add(StudentDto.toStudentDto(student));
        }
        return students;
    }

    public void deleteStudent(String className, Long studentId) {
        Optional<Group> currentClass = groupRepository.findByName(className);
        Optional<Student> existingStudent = studentRepository.findById(studentId);
        if (existingStudent.isEmpty()) {
            throw new IllegalArgumentException("Student not found");
        }

        if (currentClass.isEmpty()) {
            throw new IllegalArgumentException("Class not found.");
        }
        boolean found = false;
        for (Student student : currentClass.get().getStudents()) {
            if (existingStudent.get() == student) {
                studentRepository.delete(existingStudent.get());
                found = true;
            }
        }
        if (!found) {
            throw new IllegalArgumentException("Student not found in class.");
        }
    }
}