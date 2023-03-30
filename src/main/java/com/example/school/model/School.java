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
@Table(name = "school")
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long schoolId;
    private String schoolName;
    private String schoolAddress;

    @ManyToMany(mappedBy = "schools")
    private List<Teacher> teachers = new ArrayList<>();

    @OneToMany(
            mappedBy = "school",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Group> groups = new ArrayList<>();

    public void addGroup(Group group) {
        if (groups == null) {
            groups = new ArrayList<>();
        }
        groups.add(group);
        group.setSchool(this);
    }

    public void removeGroup(Group group) {
        groups.remove(group);
        group.setSchool(null);
    }

}
