package com.comp202.ums.Dto.course;

import com.comp202.ums.Dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {
    private Long id;
    private String courseCode;
    private String courseName;
    private String courseDesc;
    private Long creditHours;
    private CourseDeptDto department;
    private UserDto instructor;
}
