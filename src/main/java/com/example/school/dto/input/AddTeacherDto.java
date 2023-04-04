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
public class AddTeacherDto {
    private String firstName;
    private String lastName;
    private List<String> schoolNames = new ArrayList<>();
    private List<String> groupNames = new ArrayList<>();
}
