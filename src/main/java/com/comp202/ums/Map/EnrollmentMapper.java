package com.comp202.ums.Map;

import com.comp202.ums.Dto.course.CourseCreateDto;
import com.comp202.ums.Dto.course.CourseDto;
import com.comp202.ums.Dto.enrollment.EnrollmentCreateDto;
import com.comp202.ums.Dto.enrollment.EnrollmentMainDto;
import com.comp202.ums.Dto.user.UserDto;
import com.comp202.ums.Entity.Course;
import com.comp202.ums.Entity.Enrollment;
import com.comp202.ums.Entity.User;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface EnrollmentMapper {
    EnrollmentMapper INSTANCE = Mappers.getMapper(EnrollmentMapper.class);
    @Named(value = "toEnrollmentDto")
    EnrollmentMainDto toDto(Enrollment enrollment);
    @IterableMapping(qualifiedByName = "toEnrollmentDto")
    List<EnrollmentMainDto> toEnrollmentDtoList(List<Enrollment> enrollments);
    UserDto toUserDto (User user);
    CourseDto toCourseDto(Course course);
}
