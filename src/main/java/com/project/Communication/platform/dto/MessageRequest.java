package com.project.communication.platform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class MessageRequest {

    @NotNull(message = "Sender id is required")
    private Long senderId;

    private Long receiverId;

    private Long channelId;

    @NotBlank(message = "Content is required")
    private String content;
}
