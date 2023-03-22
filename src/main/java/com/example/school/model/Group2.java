//package com.example.school.model;
//
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.*;
//import java.util.HashSet;
//import java.util.Set;
//
////try with composite primary key
//@Entity
//@Getter
//@Setter
//@Table(name = "group")
//public class Group {
//    @EmbeddedId
//    private GroupName groupId = new GroupName();
//    @MapsId("studentId")
//    @OneToMany(mappedBy = "group")
//    @JoinColumn(name = "student_id")
//    private Set<Student> students = new HashSet<>();
//    @MapsId("teacherId")
//    @ManyToMany(mappedBy = "groups")
//    @JoinColumn(name = "teacher_id")
//    private Set<Teacher> teachers = new HashSet<>();
//    @MapsId("schoolId")
//    @ManyToOne
//    @JoinColumn(name = "school_id")
//    private School school;
//
//    public GroupName getGroupId() {
//        return groupId;
//    }
//
//}
