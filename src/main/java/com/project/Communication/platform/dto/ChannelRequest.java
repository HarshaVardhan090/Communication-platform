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
public class ChannelRequest {

    @NotBlank(message = "Channel name is required")
    private String name;

    private String description;

    @NotNull(message = "Community id is required")
    private Long communityId;
}
