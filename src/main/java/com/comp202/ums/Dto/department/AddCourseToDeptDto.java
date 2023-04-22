package com.comp202.ums.Dto.department;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCourseToDeptDto {
    private List<String> courseCodes;
    private String departmentCode;
}
