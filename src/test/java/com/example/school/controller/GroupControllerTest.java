package com.example.school.controller;

import com.example.school.SchoolApplication;
import com.example.school.repository.GroupRepository;
import com.example.school.repository.StudentRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = SchoolApplication.class)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class GroupControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
        groupRepository.deleteAll();
        studentRepository.deleteAll();
    }

}