package com.example.school.dto;

import com.example.school.model.School;
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
public class SchoolDto {

    private Long id;
    private String schoolName;
    @With
    private List<TeacherDto> teachers = new ArrayList<>();

    public static SchoolDto toSchoolDto(School school) {
        if (school == null) {
            return null;
        }

        SchoolDto result = SchoolDto.builder()
                .id(school.getSchoolId())
                .schoolName(school.getSchoolName())
                .build();
        if (school.getTeachers() != null) {
            result = result.withTeachers(
                    school.getTeachers().stream()
                            .map(TeacherDto::toTeacherDto)
                            .collect(Collectors.toList()));
        }
        return result;

    }
}
