package com.comp202.ums.Dto.announcement;

import com.comp202.ums.Dto.course.CourseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnnouncementCreateDto {
    private String title;
    private String description;
    private Long courseId;
}
