package com.project.communication.platform.controller;

import com.project.communication.platform.dto.AccessCheckResponse;
import com.project.communication.platform.dto.RoleResponse;
import com.project.communication.platform.dto.RoleUpdateRequest;
import com.project.communication.platform.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PutMapping("/update")
    public ResponseEntity<RoleResponse> updateUserRole(@Valid @RequestBody RoleUpdateRequest request) {
        RoleResponse response = roleService.updateUserRole(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<RoleResponse> getUserRole(@PathVariable Long userId) {
        RoleResponse response = roleService.getUserRole(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/check/admin/{userId}")
    public ResponseEntity<AccessCheckResponse> checkAdminAccess(@PathVariable Long userId) {
        AccessCheckResponse response = roleService.buildAdminAccessResponse(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/check/moderator/{userId}")
    public ResponseEntity<AccessCheckResponse> checkModeratorAccess(@PathVariable Long userId) {
        AccessCheckResponse response = roleService.buildModeratorAccessResponse(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/check/member/{userId}")
    public ResponseEntity<AccessCheckResponse> checkMemberAccess(@PathVariable Long userId) {
        AccessCheckResponse response = roleService.buildMemberAccessResponse(userId);
        return ResponseEntity.ok(response);
    }
}
