package com.example.school.dto;

import com.example.school.model.Student;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class StudentDto {

    private Long id;
    private String firstName;
    private String lastName;
    private SchoolDto school;
    private GroupDto group;

    public static StudentDto toStudentDto(Student student) {
        if (student == null) {
            return null;
        }
        return StudentDto.builder()
                .id(student.getStudentId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .group(GroupDto.toGroupDto(student.getGroup()))
                .school(SchoolDto.toSchoolDto(student.getSchool()))
                .build();
    }
}
