package com.example.school.dto;

import com.example.school.model.Group;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
public class GroupDto {

    private Long id;
    private String name;
    private SchoolDto school;

    public static GroupDto toGroupDto(Group group) {
        if (group == null) {
            return null;
        }
        return GroupDto.builder()
                .id(group.getId())
                .name(group.getName())
                .school(SchoolDto.toSchoolDto(group.getSchool()))
                .build();
    }
}
