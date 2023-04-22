package com.comp202.ums.Map;

import com.comp202.ums.Dto.course.CourseDto;
import com.comp202.ums.Entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CourseMapper {
    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

    Course DtoToCourse(CourseDto dto);
}
