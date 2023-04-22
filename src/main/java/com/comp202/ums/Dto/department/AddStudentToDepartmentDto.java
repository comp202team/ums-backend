package com.comp202.ums.Dto.department;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddStudentToDepartmentDto {
    private List<String> studentEmail;
    private String departmentCode;
}
