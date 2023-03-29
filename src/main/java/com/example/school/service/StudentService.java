package com.example.school.service;

import com.example.school.dto.StudentDto;
import com.example.school.model.Group;
import com.example.school.model.School;
import com.example.school.model.Student;
import com.example.school.repository.GroupRepository;
import com.example.school.repository.SchoolRepository;
import com.example.school.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    public List<StudentDto> getAll() {
        List<StudentDto> students = new ArrayList<>();
        for (Student student : studentRepository.findAll()) {
            students.add(StudentDto.toStudentDto(student));
        }

        return students;
    }

    public StudentDto add(String firstName, String lastName) {
        Student newStudent = Student.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();

        return StudentDto.toStudentDto(studentRepository.save(newStudent));
    }

    public StudentDto changeClass(Long studentId, Long groupId) {
        Student existingStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Student not found"));

        Group updatedGroup = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Group not found"));

        existingStudent.setGroup(updatedGroup);

        return StudentDto.toStudentDto(studentRepository.save(existingStudent));

    }

    public StudentDto changeSchool(Long studentId, Long schoolId) {
        Student transferredStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Student not found"));

        School newSchool = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new NotFoundException("School not found"));

        transferredStudent.setGroup(null);
        transferredStudent.setSchool(newSchool);

        return StudentDto.toStudentDto(studentRepository.save(transferredStudent));
    }

    public StudentDto transferAndEnroll(Long studentId, Long schoolId, String groupName) {
        Student transferredStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Student not found"));

        School newSchool = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new NotFoundException("School not found"));

        transferredStudent.setSchool(newSchool);

        Group updatedGroup = groupRepository.findByGroupName(groupName)
                .orElseThrow(() -> new NotFoundException("Class not found"));

        if (updatedGroup.getSchool() == newSchool) {
            transferredStudent.setGroup(updatedGroup);
        } else {
            transferredStudent.setGroup(null);
        }
        return StudentDto.toStudentDto(studentRepository.save(transferredStudent));
    }
}