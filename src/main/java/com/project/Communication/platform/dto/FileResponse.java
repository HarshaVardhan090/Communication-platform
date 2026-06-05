package com.project.communication.platform.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileResponse {

    private Long id;
    private String fileName;
    private String fileType;
    private String filePath;
    private Long uploadedBy;
    private Long channelId;
    private LocalDateTime uploadedAt;
}
