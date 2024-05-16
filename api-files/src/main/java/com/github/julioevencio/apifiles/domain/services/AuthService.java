package com.github.julioevencio.apifiles.domain.services;

import com.github.julioevencio.apifiles.api.v1.dto.register.RegisterRequestDTO;
import com.github.julioevencio.apifiles.api.v1.dto.register.RegisterResponseDTO;

public interface AuthService {

    RegisterResponseDTO register(RegisterRequestDTO dto);

}
