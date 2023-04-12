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
    @Column(name = "teacherId")
    private Long id;
    private String firstName;
    private String lastName;
    @ManyToMany
    @JoinTable(name = "teacher_school",
            joinColumns = @JoinColumn(name = "teacher_id", referencedColumnName = "teacherId"),
            inverseJoinColumns = @JoinColumn(name = "school_id", referencedColumnName = "schoolId"))
    private List<School> schools = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "teacher_group",
            joinColumns = @JoinColumn(name = "teacher_id", referencedColumnName = "teacherId"),
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "groupId"))
    private List<Group> groups = new ArrayList<>();

    public void addGroup(Group group) {
        if (groups == null) {
            groups = new ArrayList<>();
        }
        groups.add(group);
    }

    public void removeGroup(Group group) {
        groups.remove(group);
        group.removeTeacher(this);
    }

    public void addSchool(School school) {
        if (schools == null) {
            schools = new ArrayList<>();
        }
        schools.add(school);
    }

    public void removeSchool(School school) {
        schools.remove(school);
    }

}
