package com.github.julioevencio.apifiles.domain.services;

import com.github.julioevencio.apifiles.api.v1.dto.register.RegisterMapper;
import com.github.julioevencio.apifiles.api.v1.dto.register.RegisterRequestDTO;
import com.github.julioevencio.apifiles.api.v1.dto.register.RegisterResponseDTO;
import com.github.julioevencio.apifiles.domain.entities.RoleEntity;
import com.github.julioevencio.apifiles.domain.entities.UserEntity;
import com.github.julioevencio.apifiles.domain.exceptions.ApiFilesSQLException;
import com.github.julioevencio.apifiles.domain.repositories.RoleRepository;
import com.github.julioevencio.apifiles.domain.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public RegisterResponseDTO register(RegisterRequestDTO dto) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new ApiFilesSQLException("Username already exists");
        }

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new ApiFilesSQLException("E-mail already exists");
        }

        UserEntity user = RegisterMapper.fromDTO(dto);

        user.setEnabled(true);
        user.setCredentialsNonExpired(true);
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        List<RoleEntity> roles = new ArrayList<>();
        roles.add(roleRepository.findByName("ROLE_USER").orElseThrow(RuntimeException::new));

        user.setRoles(roles);

        return RegisterMapper.fromEntity(userRepository.save(user));
    }

}
