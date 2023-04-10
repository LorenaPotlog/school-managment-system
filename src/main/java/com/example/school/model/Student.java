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
    private Long id;
    private String firstName;
    private String lastName;
    @ManyToOne(cascade = CascadeType.MERGE)
    private School school;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "student_group")
    private Group group;


}
