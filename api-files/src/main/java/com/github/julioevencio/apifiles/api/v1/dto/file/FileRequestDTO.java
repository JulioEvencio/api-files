package com.github.julioevencio.apifiles.api.v1.dto.file;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public class FileRequestDTO implements Serializable {

    @NotBlank(message = "Invalid file")
    private final MultipartFile file;

    public FileRequestDTO(MultipartFile file) {
        this.file = file;
    }

    public MultipartFile getFile() {
        return file;
    }

}
