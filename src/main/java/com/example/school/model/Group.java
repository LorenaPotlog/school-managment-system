package com.example.school.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "class")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "groupId")
    private Long groupId;

    private String groupName;
    private int groupSize;
    @OneToMany(mappedBy = "group")
    private List<Student> students = new ArrayList<>();
    @ManyToMany(mappedBy = "groups")
    private List<Teacher> teachers = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "group_school")
    private School school;

}
