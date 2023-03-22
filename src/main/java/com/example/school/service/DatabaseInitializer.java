package com.example.school.service;

import com.example.school.model.Group;
import com.example.school.model.School;
import com.example.school.repository.GroupRepository;
import com.example.school.repository.SchoolRepository;
import com.example.school.repository.StudentRepository;
import com.example.school.repository.TeacherRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @PostConstruct
    public void init(){
        School school1 = School.builder()
                .schoolName("Sf Nicolae")
                .address("Decebal 23A")
                .build();

        School school2 = School.builder()
                .schoolName("Petru Rares")
                .address("Sfintilor 120B")
                .build();

        schoolRepository.save(school1);
        schoolRepository.save(school2);

        Group group1 = Group.builder()
                .groupSize(25)
                .groupName("I-A")
                .school(school1)
                .build();

        Group group2 = Group.builder()
                .groupSize(20)
                .groupName("II-B")
                .school(school1)
                .build();

        Group group3 = Group.builder()
                .groupSize(25)
                .groupName("III-A")
                .school(school1)
                .build();

        Group group4 = Group.builder()
                .groupSize(19)
                .groupName("VI-D")
                .school(school1)
                .build();

        groupRepository.save(group1);
        groupRepository.save(group2);
        groupRepository.save(group3);
        groupRepository.save(group4);



    }

}

