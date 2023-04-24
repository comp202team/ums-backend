package com.comp202.ums.Dto.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseCreateDto {
    private String courseCode;
    private String courseName;
    private String courseDesc;
    private long creditHours;
}
