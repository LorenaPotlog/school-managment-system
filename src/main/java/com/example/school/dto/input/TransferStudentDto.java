package com.example.school.dto.input;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TransferStudentDto {
    private Long studentId;
    private Long schoolId;
    private String groupName;
}
