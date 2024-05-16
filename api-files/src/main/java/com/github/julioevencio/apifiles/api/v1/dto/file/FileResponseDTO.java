package com.github.julioevencio.apifiles.api.v1.dto.file;

import java.io.Serializable;

public class FileResponseDTO implements Serializable {

    private final String info;

    public FileResponseDTO(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

}
