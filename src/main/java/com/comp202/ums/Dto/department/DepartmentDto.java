package com.comp202.ums.Dto.department;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDto {
    private String departmentName;
    private String departmentCode;
    private String departmentHead;
}
