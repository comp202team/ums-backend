package com.comp202.ums.Dto.email;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailMessage {
    @Email
    private String email;
    private String subject;
    private String message;
}
