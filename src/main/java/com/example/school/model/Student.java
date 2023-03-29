package com.example.school.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "studentId")
    private Long studentId;
    private String firstName;
    private String lastName;
    @ManyToOne
    private School school;

    @ManyToOne
    @JoinColumn(name = "student_group")
    private Group group;

}
