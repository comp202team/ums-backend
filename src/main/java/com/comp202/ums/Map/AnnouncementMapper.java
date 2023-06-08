package com.comp202.ums.Map;

import com.comp202.ums.Dto.announcement.AnnouncementDto;
import com.comp202.ums.Dto.course.CourseDto;
import com.comp202.ums.Entity.Announcement;
import com.comp202.ums.Entity.Course;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AnnouncementMapper {
    AnnouncementMapper INSTANCE = Mappers.getMapper(AnnouncementMapper.class);
    @Named(value = "toAnnouncementDto")
    AnnouncementDto toDto(Announcement announcement);
    @IterableMapping(qualifiedByName = "toAnnouncementDto")
    List<AnnouncementDto> toDtoList(List<Announcement> announcements);
    CourseDto toCourseDto(Course course);
}
