package com.example.school.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    @ManyToMany(mappedBy = "schools")
    private Set<Teacher> teachers = new HashSet<>();

    @OneToMany(mappedBy = "school")
    @JsonIgnore
    private List<Group> groups = new ArrayList<>();

    public School() {
    }
}
