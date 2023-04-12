package com.example.school.dto;

import com.example.school.model.School;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
public class SchoolDto {

    private Long id;
    private String name;

    public static SchoolDto toSchoolDto(School school) {
        if (school == null) {
            return null;
        }
        return SchoolDto.builder()
                .id(school.getId())
                .name(school.getName())
                .build();
    }
}
