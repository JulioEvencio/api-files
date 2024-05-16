package com.github.julioevencio.apifiles.api.v1.dto.register;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class RegisterRequestDTO implements Serializable {

    @NotBlank(message = "Invalid username")
    @Size(max = 20, min = 3, message = "The username must be between 3 and 20 characters long")
    private final String username;

    @Email(message = "Invalid e-mail")
    @Size(max = 100, min = 3, message = "The e-mail must be between 3 and 100 characters long")
    private final String email;

    @NotBlank(message = "Invalid password")
    @Size(max = 20, min = 3, message = "The password must be between 3 and 20 characters long")
    private final String password;

    public RegisterRequestDTO(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
