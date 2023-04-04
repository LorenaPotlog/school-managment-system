package com.example.school.dto.input;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AddSchoolDto {
    private String schoolName;
    private List<Long> teacherIds = new ArrayList<>();
}
