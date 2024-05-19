package com.github.julioevencio.apifiles.domain.services;

import com.github.julioevencio.apifiles.api.v1.dto.file.FileRequestDTO;
import com.github.julioevencio.apifiles.api.v1.dto.file.FileResponseDTO;

import java.io.InputStream;

public interface FileService {

    InputStream download(String fileName);

    InputStream backup();

    FileResponseDTO upload(FileRequestDTO dto);

}
