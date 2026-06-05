package com.project.communication.platform.dto;

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
public class AccessCheckResponse {

    private Long userId;
    private boolean hasAccess;
    private String role;
    private String requiredAccess;
}
