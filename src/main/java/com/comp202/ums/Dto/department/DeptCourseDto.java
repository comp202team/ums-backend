package com.comp202.ums.Dto.department;


import com.comp202.ums.Dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeptCourseDto {
    private Long id;
    private String courseCode;
    private String courseName;
    private String courseDesc;
    private Long creditHours;
    private String departmentCode;
    private UserDto instructor;
}
