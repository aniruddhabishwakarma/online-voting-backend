package com.project.System.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUser {
    @NotNull(message = "First name is required")
    @Size(min = 2, max = 20, message = "First name must be between 2 and 20 characters")
    private String fullName;
    @NotNull(message = "Username is required")
    @Size(min = 4, max = 100, message = "Email must be between 4 and 100 characters")
    private String username;
    @NotNull(message = "Email is required")
    @Size(min=4, max = 40,message = "Email must be between 4 and 40 characters")
    private String email;
    @NotNull(message = "Contact number is required")
    private Long contact;
    @NotNull(message = "Password is required")
    @Size(min = 8, max = 40, message = "Password must be between 8 and 40 characters")
    private String password;
    @NotNull(message = "Role is required")
    private Roles role;

}
