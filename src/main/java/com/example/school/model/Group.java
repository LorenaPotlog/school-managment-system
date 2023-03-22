package com.example.school.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

//try with composite primary key
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
    private int groupSize; //no of students enrolled
    @OneToMany(mappedBy = "group")
    private Set<Student> students = new HashSet<>();
    @ManyToMany(mappedBy = "groups")
    private Set<Teacher> teachers = new HashSet<>();
   @ManyToOne
   @JoinColumn(name = "group_school") //referencedColumnName = "schoolId")
   @JsonIgnore
   private School school;

    public Group() {

    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
