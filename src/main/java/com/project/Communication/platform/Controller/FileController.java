package com.project.communication.platform.controller;

import com.project.communication.platform.dto.FileResponse;
import com.project.communication.platform.service.FileStorageService;
import com.project.communication.platform.service.FileStorageService.FileDownloadResult;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
@Validated
public class FileController {

    private final FileStorageService fileStorageService;

    public FileController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<FileResponse> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("uploadedBy") @NotNull(message = "Uploaded by user id is required") Long uploadedBy,
            @RequestParam(value = "channelId", required = false) Long channelId) {
        FileResponse response = fileStorageService.uploadFile(file, uploadedBy, channelId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<FileResponse>> getAllFiles() {
        List<FileResponse> files = fileStorageService.getAllFiles();
        return ResponseEntity.ok(files);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        FileDownloadResult downloadResult = fileStorageService.downloadFile(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(downloadResult.fileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + downloadResult.fileName() + "\"")
                .body(downloadResult.resource());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FileResponse>> getFilesByUser(@PathVariable Long userId) {
        List<FileResponse> files = fileStorageService.getFilesByUser(userId);
        return ResponseEntity.ok(files);
    }

    @GetMapping("/channel/{channelId}")
    public ResponseEntity<List<FileResponse>> getFilesByChannel(@PathVariable Long channelId) {
        List<FileResponse> files = fileStorageService.getFilesByChannel(channelId);
        return ResponseEntity.ok(files);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long id) {
        fileStorageService.deleteFile(id);
        return ResponseEntity.noContent().build();
    }
}
