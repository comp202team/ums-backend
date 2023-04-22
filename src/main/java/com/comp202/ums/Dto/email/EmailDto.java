package com.comp202.ums.Dto.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDto {
    @Email
    private String email;
}
