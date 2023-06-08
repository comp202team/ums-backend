package com.comp202.ums.Dto.announcement;

import com.comp202.ums.Dto.course.CourseDto;
import com.comp202.ums.Entity.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnnouncementDto {
    private Long id;
    private String title;
    private LocalDate date;
    private String description;
    private CourseDto course;
}
