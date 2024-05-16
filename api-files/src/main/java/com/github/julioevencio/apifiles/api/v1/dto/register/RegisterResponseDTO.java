package com.github.julioevencio.apifiles.api.v1.dto.register;

import java.io.Serializable;
import java.util.UUID;

public class RegisterResponseDTO implements Serializable {

    private final UUID uuid;
    private final String username;
    private final String email;

    public RegisterResponseDTO(UUID uuid, String username, String email) {
        this.uuid = uuid;
        this.username = username;
        this.email = email;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

}
