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
public class ChannelResponse {

    private Long id;
    private String name;
    private String description;
    private Long communityId;
    private LocalDateTime createdAt;
}
