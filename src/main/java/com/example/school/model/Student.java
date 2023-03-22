package com.example.school.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor

@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "studentId")
    private Long studentId;
    private String firstName;
    private String lastName;
    @ManyToOne
    @JoinColumn(name="student_school") //referencedColumnName = "schoolId") //student table will have a school_id column (foreign
    private School school;

    @ManyToOne
    @JoinColumn(name="student_group") //referencedColumnName = "groupId") //student table will have a school_id column (foreign
    private Group group;

    public Student() {

    }
}
