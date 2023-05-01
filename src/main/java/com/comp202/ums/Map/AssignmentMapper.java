package com.comp202.ums.Map;

import com.comp202.ums.Dto.assignment.AssignmentDto;
import com.comp202.ums.Dto.course.CourseDto;
import com.comp202.ums.Dto.submission.SubmissionDto;
import com.comp202.ums.Entity.Assignment;
import com.comp202.ums.Entity.Course;
import com.comp202.ums.Entity.Submission;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper
public interface AssignmentMapper {
    AssignmentMapper INSTANCE = Mappers.getMapper(AssignmentMapper.class);
    @Named(value = "toAssignmentDto")
    AssignmentDto toDto(Assignment assignment);
    @IterableMapping(qualifiedByName = "toAssignmentDto")
    List<AssignmentDto> toAssignmentListDto(List<Assignment> assignments);
    CourseDto toCourseDto(Course course);
    @Named(value = "toSubmissionDto")
    @Mapping(source = "assignment.name",target = "assignmentName")
    SubmissionDto toSubmissionDto(Submission submission);
    @IterableMapping(qualifiedByName = "toSubmissionDto")
    Set<SubmissionDto> toSubmissionSetDto (Set<Submission> submissions);
}
