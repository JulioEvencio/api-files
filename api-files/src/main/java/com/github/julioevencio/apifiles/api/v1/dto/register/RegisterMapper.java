package com.github.julioevencio.apifiles.api.v1.dto.register;

import com.github.julioevencio.apifiles.domain.entities.UserEntity;

public class RegisterMapper {

    public static UserEntity fromDTO(RegisterRequestDTO dto) {
        return new UserEntity(null, dto.getUsername(), dto.getEmail(), dto.getPassword(), false, false, false, false, null);
    }

    public static RegisterResponseDTO fromEntity(UserEntity entity) {
        return new RegisterResponseDTO(entity.getUuid(), entity.getUsername(), entity.getEmail());
    }

}
