package com.comp202.ums.Dto.enrollment;

import com.comp202.ums.Dto.course.CourseDto;
import com.comp202.ums.Dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentMainDto {
    private Long enrollmentId;
    private UserDto student;
    private CourseDto course;
    private String enrolmentDate;
    private Double grade;
}
