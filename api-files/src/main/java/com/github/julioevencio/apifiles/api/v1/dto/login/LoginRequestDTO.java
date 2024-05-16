package com.github.julioevencio.apifiles.api.v1.dto.login;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class LoginRequestDTO implements Serializable {

    @NotBlank(message = "Invalid username")
    @Size(max = 20, min = 3, message = "The username must be between 3 and 20 characters long")
    private final String username;

    @NotBlank(message = "Invalid password")
    @Size(max = 20, min = 3, message = "The password must be between 3 and 20 characters long")
    private final String password;

    public LoginRequestDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
