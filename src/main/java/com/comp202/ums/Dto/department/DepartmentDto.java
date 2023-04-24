package com.comp202.ums.Dto.department;

import com.comp202.ums.Dto.course.CourseDto;
import com.comp202.ums.Dto.user.UserDto;
import com.comp202.ums.Entity.Course;
import com.comp202.ums.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDto {
    private Long departmentId;
    private String departmentName;
    private String departmentCode;
    private String departmentHead;
    private List<CourseDto> offeredCourses;
    private List<UserDto> enrolledStudents;
}
