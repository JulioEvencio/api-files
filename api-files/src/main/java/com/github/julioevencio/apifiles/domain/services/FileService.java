package com.github.julioevencio.apifiles.domain.services;

import com.github.julioevencio.apifiles.api.v1.dto.file.FileRequestDTO;
import com.github.julioevencio.apifiles.api.v1.dto.file.FileResponseDTO;

public interface FileService {

    FileResponseDTO upload(FileRequestDTO dto);

}
