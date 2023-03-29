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

    public static TeacherDto ToTeacherDto(Teacher teacher) {
        if (teacher == null) {
            return null;
        }
        TeacherDto result = TeacherDto.builder()
                .id(teacher.getTeacherId())
                .firstName(teacher.getFirstName())
                .lastName(teacher.getLastName())
                .build();

        if (teacher.getGroups() != null) {
            result.toBuilder()
                    .groups(teacher.getGroups().stream()
                            .map(GroupDto::toGroupDto)
                            .collect(Collectors.toList()))
                    .build();
        }
        if (teacher.getSchools() != null) {
            result.toBuilder()
                    .schools(teacher.getSchools().stream()
                            .map(SchoolDto::toSchoolDto)
                            .collect(Collectors.toList()))
                    .build();
        }
        return result;
    }

}
