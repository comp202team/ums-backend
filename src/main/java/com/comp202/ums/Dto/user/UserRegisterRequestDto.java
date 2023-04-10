package com.comp202.ums.Dto.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequestDto {

    @NotEmpty
    @Size(min = 2, message = "user name should have at least 2 characters")
    private String username;

    @NotEmpty
    private String password;

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;
}
