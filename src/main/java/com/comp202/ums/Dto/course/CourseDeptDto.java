package com.comp202.ums.Dto.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDeptDto {
    private Long departmentId;
    private String departmentName;
    private String departmentCode;
    private String departmentHead;
}
