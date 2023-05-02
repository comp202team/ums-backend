package com.comp202.ums.Map;

import com.comp202.ums.Dto.course.CourseDeptDto;
import com.comp202.ums.Dto.course.CourseDto;
import com.comp202.ums.Dto.department.DepartmentDto;
import com.comp202.ums.Dto.user.UserDto;
import com.comp202.ums.Entity.Course;
import com.comp202.ums.Entity.Department;
import com.comp202.ums.Entity.User;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;


import java.util.List;

@Mapper
public interface CourseMapper {


    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);
    @Named(value = "toCourseDto")
    CourseDto toDto (Course course);
    @IterableMapping(qualifiedByName = "toCourseDto")
    List<CourseDto> toCourseDtoList(List<Course> courses);
    default CourseDeptDto toDepartmentDto(Department department){
        CourseDeptDto courseDeptDto = new CourseDeptDto();
        courseDeptDto.setDepartmentId(department.getDepartmentId());
        courseDeptDto.setDepartmentHead(department.getDepartmentHead());
        courseDeptDto.setDepartmentName(department.getDepartmentName());
        courseDeptDto.setDepartmentCode(department.getDepartmentCode());
        return courseDeptDto;
    }
    @Named(value = "toUserDto")
    UserDto toUserDto(User user);
}
