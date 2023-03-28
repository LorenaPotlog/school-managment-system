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
@AllArgsConstructor
@Builder
@Table(name = "class")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "groupId")
    private Long groupId;

    private String groupName;
    private int groupSize; //max. no of students that can be enrolled
    @OneToMany(mappedBy = "group")
    @JsonIgnore
    private List<Student> students = new ArrayList<>();
    @ManyToMany(mappedBy = "groups")
    @JsonIgnore
    private Set<Teacher> teachers = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "group_school")
    private School school;

    public Group() {
    }

}
