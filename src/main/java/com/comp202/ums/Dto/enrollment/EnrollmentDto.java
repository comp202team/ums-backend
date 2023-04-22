package com.comp202.ums.Dto.enrollment;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentDto {
    @Email
    private String email;
    private String courseCode;
}
