package com.example.school.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor

@Table(name = "school")
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long schoolId;

    private String schoolName;
    private String address;
    @OneToMany(mappedBy = "school") //done by the school in student
    private Set<Student> students = new HashSet<>();
    @ManyToMany(mappedBy = "schools")
    private Set<Teacher> teachers = new HashSet<>();

    @OneToMany(mappedBy = "school")
//    @JoinColumn(name = "group_from_school")
    private Set<Group> groups = new HashSet<>();

    public School() {

    }
}
