package com.comp202.ums.Map;

import com.comp202.ums.Dto.assignment.AssignmentDto;
import com.comp202.ums.Dto.submission.SubmissionDto;
import com.comp202.ums.Dto.user.UserDto;
import com.comp202.ums.Entity.Assignment;
import com.comp202.ums.Entity.Submission;
import com.comp202.ums.Entity.User;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubmissionMapper {
    SubmissionMapper INSTANCE = Mappers.getMapper(SubmissionMapper.class);
    @Named(value = "toSubmissionDto")
    @Mapping(target = "assignmentName", source = "assignment.name")
    SubmissionDto toDto(Submission submission);
    @IterableMapping(qualifiedByName = "toSubmissionDto")
    List<SubmissionDto> toSubmissionDtoList(List<Submission> submissions);
    UserDto toUserDto(User user);
}
