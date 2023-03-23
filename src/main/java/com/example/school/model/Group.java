package com.example.school.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Set<Student> students = new HashSet<>();
    @ManyToMany(mappedBy = "groups")
    @JsonIgnore
    private Set<Teacher> teachers = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "group_school")
    private School school;

    public Group() {
    }

}
