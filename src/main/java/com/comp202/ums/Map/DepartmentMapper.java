package com.comp202.ums.Map;

import com.comp202.ums.Dto.department.DepartmentDto;
import com.comp202.ums.Dto.department.DeptCourseDto;
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
public interface DepartmentMapper {
    DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);
    @Named(value = "toDepartmentDto")
    DepartmentDto toDto (Department department);
    @IterableMapping(qualifiedByName = "toDepartmentDto")
    List<DepartmentDto> toDepartmentDtoList(List<Department> departments);
    @Named(value = "toCourseDto")
    @Mapping(target = "departmentCode", source = "department.departmentCode")
    DeptCourseDto toCourseDto (Course course);
    @IterableMapping(qualifiedByName = "toCourseDto")
    List<DeptCourseDto> courseListToCourseDtoList(List<Course> courses);
    @Named(value = "toUserDto")
    UserDto toUserDto (User user);
    @IterableMapping(qualifiedByName = "toUserDto")
    List<UserDto> userListToUserDtoList(List<User> users);
}
