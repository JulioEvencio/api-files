package com.github.julioevencio.apifiles.domain.services;

import com.github.julioevencio.apifiles.api.v1.dto.file.FileRequestDTO;
import com.github.julioevencio.apifiles.api.v1.dto.file.FileResponseDTO;
import com.github.julioevencio.apifiles.domain.exceptions.custom.ApiFilesBackupException;
import com.github.julioevencio.apifiles.domain.exceptions.custom.ApiFilesDownloadException;
import com.github.julioevencio.apifiles.domain.exceptions.custom.ApiFilesUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FileServiceImpl implements FileService {

    private final Path uploadsDir;
    private final Path backupsDir;

    public FileServiceImpl(@Value("${files.directory.uploads}") String uploadsDir, @Value("${files.directory.backups}") String backupsDir) {
        this.uploadsDir = Paths.get(uploadsDir).toAbsolutePath().normalize();

        if (!this.uploadsDir.toFile().exists()) {
            boolean success = this.uploadsDir.toFile().mkdir();

            if (!success) {
                throw new RuntimeException();
            }
        }

        this.backupsDir = Paths.get(backupsDir).toAbsolutePath().normalize();

        if (!this.backupsDir.toFile().exists()) {
            boolean success = this.backupsDir.toFile().mkdir();

            if (!success) {
                throw new RuntimeException();
            }
        }
    }

    @Override
    public InputStream download(String fileName) {
        try {
            Path file = uploadsDir.resolve(this.getUserDir() + File.separator + fileName);

            return new FileInputStream(file.toAbsolutePath().toString());
        } catch (FileNotFoundException e) {
            throw new ApiFilesDownloadException("File not found");
        }
    }

    @Override
    public InputStream backup() {
        try {
            this.createZipBackup();
            Path file = backupsDir.resolve(this.getUserDir());
            InputStream zipFile = new FileInputStream(file.toAbsolutePath().toString());
            Files.deleteIfExists(file);

            return zipFile;
        } catch (ApiFilesBackupException e) {
            throw new ApiFilesBackupException(e.getMessage());
        } catch (IOException e) {
            throw new ApiFilesBackupException("Backup error");
        }
    }

    @Override
    public FileResponseDTO upload(FileRequestDTO dto) {
        try {
            String fileName = StringUtils.cleanPath(dto.getFile().getOriginalFilename());
            Path targetLocation = uploadsDir.resolve(this.getUserDir() + File.separator + fileName);

            dto.getFile().transferTo(targetLocation);

            return new FileResponseDTO("Upload completed!");
        } catch (Exception e) {
            throw new ApiFilesUploadException("Invalid file");
        }
    }

    private String getUserDir() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        File directory = new File(this.uploadsDir + File.separator + username);

        if (!directory.exists()) {
            boolean success = directory.mkdir();

            if (!success) {
                throw new RuntimeException();
            }
        }

        return username;
    }

    private void createZipBackup() throws ApiFilesBackupException, IOException {
        byte[] buf = new byte[1024];
        String zipName = this.backupsDir + File.separator + this.getUserDir();
        File dir = new File(this.uploadsDir + File.separator + this.getUserDir());
        File[] files = dir.listFiles();

        if (files == null) {
            throw new ApiFilesBackupException("No file found for backup");
        }

        try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipName))) {
            for (File file : files) {
                try (InputStream in = new FileInputStream(file)) {
                    out.putNextEntry(new ZipEntry(file.getName()));

                    int len;

                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }

                    out.closeEntry();
                }
            }
        }
    }

}
