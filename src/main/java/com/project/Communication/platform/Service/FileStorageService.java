package com.project.communication.platform.service;

import com.project.communication.platform.dto.FileResponse;
import com.project.communication.platform.entity.FileDetails;
import com.project.communication.platform.exception.FileNotFoundException;
import com.project.communication.platform.exception.FileStorageException;
import com.project.communication.platform.repository.FileDetailsRepository;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    private static final String UPLOAD_DIR = "uploads";

    private final FileDetailsRepository fileDetailsRepository;

    public FileStorageService(FileDetailsRepository fileDetailsRepository) {
        this.fileDetailsRepository = fileDetailsRepository;
    }

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR));
        } catch (IOException ex) {
            throw new FileStorageException("Could not create upload directory", ex);
        }
    }

    @Transactional
    public FileResponse uploadFile(MultipartFile file, Long uploadedBy, Long channelId) {
        if (file == null || file.isEmpty()) {
            throw new FileStorageException("File must not be empty");
        }

        String originalFileName = StringUtils.cleanPath(
                Objects.requireNonNull(file.getOriginalFilename(), "File name is required"));

        if (originalFileName.contains("..")) {
            throw new FileStorageException("Invalid file name: " + originalFileName);
        }

        String storedFileName = UUID.randomUUID() + "_" + originalFileName;
        Path targetPath = Paths.get(UPLOAD_DIR).resolve(storedFileName);

        try {
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new FileStorageException("Failed to store file: " + originalFileName, ex);
        }

        String fileType = file.getContentType() != null ? file.getContentType() : "application/octet-stream";

        FileDetails fileDetails = FileDetails.builder()
                .fileName(originalFileName)
                .fileType(fileType)
                .filePath(targetPath.toString())
                .uploadedBy(uploadedBy)
                .channelId(channelId)
                .uploadedAt(LocalDateTime.now())
                .build();

        FileDetails savedFile = fileDetailsRepository.save(fileDetails);
        return mapToFileResponse(savedFile);
    }

    @Transactional(readOnly = true)
    public FileDownloadResult downloadFile(Long fileId) {
        FileDetails fileDetails = fileDetailsRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("File not found with id: " + fileId));

        try {
            Path filePath = Paths.get(fileDetails.getFilePath());
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                throw new FileNotFoundException("File not found on disk with id: " + fileId);
            }

            return new FileDownloadResult(resource, fileDetails.getFileName(), fileDetails.getFileType());
        } catch (IOException ex) {
            throw new FileStorageException("Failed to read file with id: " + fileId, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<FileResponse> getAllFiles() {
        return fileDetailsRepository.findAll().stream()
                .map(this::mapToFileResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<FileResponse> getFilesByUser(Long uploadedBy) {
        return fileDetailsRepository.findByUploadedBy(uploadedBy).stream()
                .map(this::mapToFileResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<FileResponse> getFilesByChannel(Long channelId) {
        return fileDetailsRepository.findByChannelId(channelId).stream()
                .map(this::mapToFileResponse)
                .toList();
    }

    @Transactional
    public void deleteFile(Long id) {
        FileDetails fileDetails = fileDetailsRepository.findById(id)
                .orElseThrow(() -> new FileNotFoundException("File not found with id: " + id));

        try {
            Path filePath = Paths.get(fileDetails.getFilePath());
            Files.deleteIfExists(filePath);
        } catch (IOException ex) {
            throw new FileStorageException("Failed to delete file with id: " + id, ex);
        }

        fileDetailsRepository.delete(fileDetails);
    }

    private FileResponse mapToFileResponse(FileDetails fileDetails) {
        return FileResponse.builder()
                .id(fileDetails.getId())
                .fileName(fileDetails.getFileName())
                .fileType(fileDetails.getFileType())
                .filePath(fileDetails.getFilePath())
                .uploadedBy(fileDetails.getUploadedBy())
                .channelId(fileDetails.getChannelId())
                .uploadedAt(fileDetails.getUploadedAt())
                .build();
    }

    public record FileDownloadResult(Resource resource, String fileName, String fileType) {
    }
}
