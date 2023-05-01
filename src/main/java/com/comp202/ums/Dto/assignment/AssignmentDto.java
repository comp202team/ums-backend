package com.comp202.ums.Dto.assignment;

import com.comp202.ums.Dto.course.CourseDto;
import com.comp202.ums.Dto.submission.SubmissionDto;
import com.comp202.ums.Entity.Course;
import com.comp202.ums.Entity.Submission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentDto {
    private Long id;
    private String name;
    private String deadline;
    private CourseDto course;
    private Set<SubmissionDto> submissions;
}
