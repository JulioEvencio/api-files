package com.github.julioevencio.apifiles.api.v1.dto.login;

import java.io.Serializable;

public class LoginResponseDTO implements Serializable {

    private final String accessToken;

    public LoginResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

}
