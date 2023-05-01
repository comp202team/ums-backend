package com.comp202.ums.Dto.enrollment;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentCreateDto {
    @Email
    private String email;
    private Long courseId;
}
