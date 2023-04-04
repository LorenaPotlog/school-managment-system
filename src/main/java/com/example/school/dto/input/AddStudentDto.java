package com.example.school.dto.input;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AddStudentDto {
    private String firstName;
    private String lastName;
    private String schoolName;
    private String groupName;

}
