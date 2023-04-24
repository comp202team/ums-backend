package com.comp202.ums.Dto.submission;

import com.comp202.ums.Dto.assignment.AssignmentDto;
import com.comp202.ums.Dto.user.UserDto;
import com.comp202.ums.Entity.Assignment;
import com.comp202.ums.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionDto {
    private Long id;
    private String title;
    private String content;
    private String submissionTime;
    private Double grade;
    private UserDto student;
    private AssignmentDto assignment;
}
