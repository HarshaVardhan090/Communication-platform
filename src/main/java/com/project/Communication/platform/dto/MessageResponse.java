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
public class MessageResponse {

    private Long id;
    private Long senderId;
    private Long receiverId;
    private Long channelId;
    private String content;
    private LocalDateTime sentAt;
}
