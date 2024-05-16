package com.github.julioevencio.apifiles.domain.services;

import com.github.julioevencio.apifiles.api.v1.dto.file.FileRequestDTO;
import com.github.julioevencio.apifiles.api.v1.dto.file.FileResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements FileService {

    private final Path directory;

    public FileServiceImpl(@Value("${files.directory}") String directory) {
        this.directory = Paths.get(directory).toAbsolutePath().normalize();

        if (!this.directory.toFile().exists()) {
            boolean success = this.directory.toFile().mkdir();

            if (!success) {
                throw new RuntimeException();
            }
        }
    }

    @Override
    public FileResponseDTO upload(FileRequestDTO dto) {
        String fileName = StringUtils.cleanPath(dto.getFile().getOriginalFilename());
        Path targetLocation = directory.resolve(this.getDirectory() + File.separator + fileName);

        try {
            dto.getFile().transferTo(targetLocation);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new FileResponseDTO("Upload completed!");
    }

    private String getDirectory() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        File directory = new File(this.directory + File.separator + username);

        if (!directory.exists()) {
            boolean success = directory.mkdir();

            if (!success) {
                throw new RuntimeException();
            }
        }

        return username;
    }

}
