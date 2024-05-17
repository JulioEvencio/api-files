package com.github.julioevencio.apifiles.api.v1.controllers;

import com.github.julioevencio.apifiles.api.v1.dto.file.FileRequestDTO;
import com.github.julioevencio.apifiles.api.v1.dto.file.FileResponseDTO;
import com.github.julioevencio.apifiles.domain.exceptions.ApiFilesMessageError;
import com.github.julioevencio.apifiles.domain.services.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping(path = "/v1/api/files")
@Tag(name = "Files", description = "Endpoints for files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping(path = "/{fileName:.+}", produces = MediaType.ALL_VALUE)
    @Operation(
            security = @SecurityRequirement(name = "bearerAuth"),
            summary = "Download file by name",
            description = "Download file by name",
            tags = {"Files"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Download completed",
                            content = @Content(
                                    mediaType = MediaType.ALL_VALUE,
                                    schema = @Schema(implementation = InputStreamResource.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiFilesMessageError.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiFilesMessageError.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiFilesMessageError.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Unprocessable entity",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiFilesMessageError.class)
                            )
                    )
            }
    )
    public ResponseEntity<InputStreamResource> download(@PathVariable String fileName) {
        InputStream file = fileService.download(fileName);
        InputStreamResource response = new InputStreamResource(file);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            security = @SecurityRequirement(name = "bearerAuth"),
            summary = "Upload a file",
            description = "Upload a file",
            tags = {"Files"},
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Upload completed",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = FileResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiFilesMessageError.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiFilesMessageError.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Unprocessable entity",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiFilesMessageError.class)
                            )
                    )
            }
    )
    public ResponseEntity<FileResponseDTO> upload(@RequestParam MultipartFile file) {
        FileResponseDTO response = fileService.upload(new FileRequestDTO(file));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
