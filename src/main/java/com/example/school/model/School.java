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

    @OneToMany(mappedBy = "school")
    private List<Group> groups = new ArrayList<>();

}
