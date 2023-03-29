package com.example.school.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "teacher")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teacherId;
    private String firstName;
    private String lastName;
    @ManyToMany
    @JoinTable(name = "teacher_school",
            joinColumns = @JoinColumn(name = "teacher_id", referencedColumnName = "teacherId"),
            inverseJoinColumns = @JoinColumn(name = "school_id", referencedColumnName = "schoolId"))
    private List<School> schools = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "teacher_group",
            joinColumns = @JoinColumn(name = "teacher_id", referencedColumnName = "teacherId"),
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "groupId"))
    private List<Group> groups = new ArrayList<>();

}
