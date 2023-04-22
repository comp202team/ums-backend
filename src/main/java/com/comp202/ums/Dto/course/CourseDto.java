package com.comp202.ums.Dto.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {
    private String courseCode;
    private String courseName;
    private String coursedesc;
    private long creditHours;
}
