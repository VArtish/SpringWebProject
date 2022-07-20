package com.example.webapplication.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpDto {
    @NotNull(message = "This field is required!")
    @Size(min = 6, max = 20, message = "Size can be between 6 and 20")
    private String username;


    @NotNull(message = "This field is required!")
    @Size(min = 9, max = 50, message = "Size can be between 6 and 50")
    @Email(message = "Not correct email!")
    private String email;

    @NotNull(message = "This field is required!")
    @Size(min = 5, max = 16, message = "Size can be between 5 and 16")
    private String password;

    @NotNull(message = "This field is required!")
    @Size(min = 5, max = 16, message = "Size can be between 5 and 16")
    private String repPassword;
}
