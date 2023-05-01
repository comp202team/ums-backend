package com.comp202.ums.Dto.assignment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentCreateDto {
    private String name;
    private String deadline;
    private String description;
}
