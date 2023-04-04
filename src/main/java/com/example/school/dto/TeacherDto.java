package com.example.school.dto;

import com.example.school.model.Teacher;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
public class TeacherDto {
    private Long id;
    private String firstName;
    private String lastName;

    private List<SchoolDto> schools = new ArrayList<>();

    private List<GroupDto> groups = new ArrayList<>();

    public static TeacherDto toTeacherDto(Teacher teacher) {
        if (teacher == null) {
            return null;
        }
        TeacherDto result = TeacherDto.builder()
                .id(teacher.getTeacherId())
                .firstName(teacher.getFirstName())
                .lastName(teacher.getLastName())
                .build();

        if (teacher.getGroups() != null) {
            result = result.withGroups(
                    teacher.getGroups().stream()
                            .map(GroupDto::toGroupDto)
                            .collect(Collectors.toList()));
        }
        if (teacher.getSchools() != null) {
            result = result.withSchools(
                    teacher.getSchools().stream()
                            .map(SchoolDto::toSchoolDto)
                            .collect(Collectors.toList()));
        }
        return result;
    }

    public TeacherDto withSchools(List<SchoolDto> schools) {
        return this.schools == schools ? this : new TeacherDto(this.id, this.firstName, this.lastName, schools, this.groups);
    }

    public TeacherDto withGroups(List<GroupDto> groups) {
        return this.groups == groups ? this : new TeacherDto(this.id, this.firstName, this.lastName, this.schools, groups);
    }
}
