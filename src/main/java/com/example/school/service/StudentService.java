package com.example.school.service;

import com.example.school.dto.StudentDto;
import com.example.school.dto.input.AddStudentDto;
import com.example.school.dto.input.TransferStudentDto;
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
import java.util.Optional;

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

    public StudentDto add(AddStudentDto addStudentDto) {
        Student newStudent = Student.builder()
                .firstName(addStudentDto.getFirstName())
                .lastName(addStudentDto.getLastName())
                .build();

        Optional<Group> group = groupRepository.findByName(addStudentDto.getGroupName());
        Optional<School> school = schoolRepository.findByName(addStudentDto.getSchoolName());

        if (group.isEmpty()) {
            throw new NotFoundException("Group not found.");
        } else newStudent.setGroup(group.get());

        if (school.isEmpty()) {
            throw new NotFoundException("School not found.");
        } else newStudent.setSchool(school.get());

        return StudentDto.toStudentDto(studentRepository.save(newStudent));
    }

    public void delete(String groupName, Long studentId) {
        Group currentGroup = groupRepository.findByName(groupName)
                .orElseThrow(() -> new NotFoundException("Group not found"));
        Student existingStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Student not found"));

        boolean found = false;
        for (Student student : currentGroup.getStudents()) {
            if (existingStudent == student) {
                studentRepository.delete(existingStudent);
                found = true;
            }
        }
        if (!found) {
            throw new NotFoundException("Student not found in class.");
        }
    }

    public StudentDto changeGroup(Long studentId, Long groupId) {
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

    public StudentDto changeSchoolAndGroup(TransferStudentDto transferStudentDto) {
        Student transferredStudent = studentRepository.findById(transferStudentDto.getStudentId())
                .orElseThrow(() -> new NotFoundException("Student not found"));
        School newSchool = schoolRepository.findById(transferStudentDto.getSchoolId())
                .orElseThrow(() -> new NotFoundException("School not found"));

        transferredStudent.setSchool(newSchool);

        Group newGroup = groupRepository.findByName(transferStudentDto.getGroupName())
                .orElseThrow(() -> new NotFoundException("Group not found"));

        if (newGroup.getSchool() == newSchool) {
            transferredStudent.setGroup(newGroup);
        } else {
            transferredStudent.setGroup(null);
        }
        return StudentDto.toStudentDto(studentRepository.save(transferredStudent));
    }
}
