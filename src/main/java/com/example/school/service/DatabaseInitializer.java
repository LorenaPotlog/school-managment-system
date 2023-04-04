package com.example.school.service;

import com.example.school.model.Group;
import com.example.school.model.School;
import com.example.school.model.Student;
import com.example.school.model.Teacher;
import com.example.school.repository.GroupRepository;
import com.example.school.repository.SchoolRepository;
import com.example.school.repository.StudentRepository;
import com.example.school.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Used for manual testing. Currently not used in order to run integration tests.
 **/
@Service
public class DatabaseInitializer {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    //    @PostConstruct
    public void init() {
        School school1 = School.builder().schoolName("Sf Nicolae").schoolAddress("Decebal 23A").build();

        School school2 = School.builder().schoolName("Petru Rares").schoolAddress("Sfintilor 120B").build();

        schoolRepository.save(school1);
        schoolRepository.save(school2);

        Group group1 = Group.builder().groupSize(25).groupName("I-A").school(school1).build();

        Group group2 = Group.builder().groupSize(20).groupName("II-B").school(school1).build();

        Group group3 = Group.builder().groupSize(25).groupName("III-A").school(school2).build();

        Group group4 = Group.builder().groupSize(19).groupName("VI-D").school(school2).build();

        groupRepository.save(group1);
        groupRepository.save(group2);
        groupRepository.save(group3);
        groupRepository.save(group4);

        Teacher teacher1 = Teacher.builder().firstName("Mihai").lastName("Popa").groups(List.of(group1, group2)).build();

        Teacher teacher2 = Teacher.builder().firstName("Maria").lastName("Iorga").groups(List.of(group1)).build();

        Teacher teacher3 = Teacher.builder().firstName("Andreea").lastName("Ionescu").groups(List.of(group1, group3)).build();

        Teacher teacher4 = Teacher.builder().firstName("Dan").lastName("Alexandru").groups(List.of(group1, group2, group4)).build();

        teacherRepository.save(teacher1);
        teacherRepository.save(teacher2);
        teacherRepository.save(teacher3);
        teacherRepository.save(teacher4);



        Student student1 = Student.builder().firstName("Mircea").lastName("Popa").group(group1).build();

        Student student2 = Student.builder().firstName("Andi").lastName("Mihai").group(group1).build();

        Student student3 = Student.builder().firstName("Maria").lastName("M").group(group1).build();

        Student student4 = Student.builder().firstName("Andrei").lastName("B").group(group2).build();

        Student student5 = Student.builder().firstName("Bianca").lastName("R").group(group2).build();

        Student student6 = Student.builder().firstName("Raluca").lastName("T").group(group3).build();

        Student student7 = Student.builder().firstName("Teodor").lastName("L").group(group3).build();

        Student student8 = Student.builder().firstName("Cristian").lastName("E").group(group3).build();

        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);
        studentRepository.save(student4);
        studentRepository.save(student5);
        studentRepository.save(student6);
        studentRepository.save(student7);
        studentRepository.save(student8);
    }

}

