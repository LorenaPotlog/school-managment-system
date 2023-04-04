package com.example.school.dto.input;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AddGroupDto {
    private String groupName;
    private String schoolName;
}
