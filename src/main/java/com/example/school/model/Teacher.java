package com.example.school.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@Table(name = "teacher")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teacherId;
    private String firstName; //@JoinColumn(name="school_id", referencedColumnName = "schoolId"
    private String lastName;
    @ManyToMany
    @JoinTable(name="teacher_school",
            joinColumns = @JoinColumn(name="teacher_id",referencedColumnName = "teacherId"),
            inverseJoinColumns = @JoinColumn(name="school_id",referencedColumnName = "schoolId"))
    private List<School> schools = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "teacher_group",
            joinColumns = @JoinColumn(name = "teacher_id",referencedColumnName = "teacherId"),
            inverseJoinColumns = @JoinColumn(name = "group_id",referencedColumnName = "groupId"))
    private List<Group> groups = new ArrayList<>();

    public Teacher() {

    }
}
