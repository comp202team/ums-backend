package com.comp202.ums.Map;

import com.comp202.ums.Dto.course.CourseDto;
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
    @Mapping(target = "departmentCode",source = "department.departmentCode")
    CourseDto toDto (Course course);
    @IterableMapping(qualifiedByName = "toCourseDto")
    List<CourseDto> toCourseDtoList(List<Course> courses);
    @Named(value = "toUserDto")
    UserDto toUserDto(User user);
}
