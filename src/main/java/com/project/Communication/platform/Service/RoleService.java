package com.project.communication.platform.service;

import com.project.communication.platform.dto.AccessCheckResponse;
import com.project.communication.platform.dto.RoleResponse;
import com.project.communication.platform.dto.RoleUpdateRequest;
import com.project.communication.platform.entity.User;
import com.project.communication.platform.entity.UserRole;
import com.project.communication.platform.exception.InvalidRoleException;
import com.project.communication.platform.exception.UserNotFoundException;
import com.project.communication.platform.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService {

    private final UserRepository userRepository;

    public RoleService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public RoleResponse updateUserRole(RoleUpdateRequest request) {
        if (!UserRole.isValid(request.getRole())) {
            throw new InvalidRoleException("Invalid role. Allowed roles: ADMIN, MODERATOR, MEMBER");
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + request.getUserId()));

        user.setRole(UserRole.fromString(request.getRole()).name());
        User updatedUser = userRepository.save(user);
        return mapToRoleResponse(updatedUser);
    }

    @Transactional(readOnly = true)
    public RoleResponse getUserRole(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        return mapToRoleResponse(user);
    }

    @Transactional(readOnly = true)
    public boolean checkAdminAccess(Long userId) {
        UserRole role = getUserRoleEnum(userId);
        return role == UserRole.ADMIN;
    }

    @Transactional(readOnly = true)
    public boolean checkModeratorAccess(Long userId) {
        UserRole role = getUserRoleEnum(userId);
        return role == UserRole.ADMIN || role == UserRole.MODERATOR;
    }

    @Transactional(readOnly = true)
    public boolean checkMemberAccess(Long userId) {
        UserRole role = getUserRoleEnum(userId);
        return role == UserRole.ADMIN || role == UserRole.MODERATOR || role == UserRole.MEMBER;
    }

    @Transactional(readOnly = true)
    public AccessCheckResponse buildAdminAccessResponse(Long userId) {
        User user = findUser(userId);
        return AccessCheckResponse.builder()
                .userId(userId)
                .hasAccess(checkAdminAccess(userId))
                .role(user.getRole())
                .requiredAccess(UserRole.ADMIN.name())
                .build();
    }

    @Transactional(readOnly = true)
    public AccessCheckResponse buildModeratorAccessResponse(Long userId) {
        User user = findUser(userId);
        return AccessCheckResponse.builder()
                .userId(userId)
                .hasAccess(checkModeratorAccess(userId))
                .role(user.getRole())
                .requiredAccess(UserRole.MODERATOR.name())
                .build();
    }

    @Transactional(readOnly = true)
    public AccessCheckResponse buildMemberAccessResponse(Long userId) {
        User user = findUser(userId);
        return AccessCheckResponse.builder()
                .userId(userId)
                .hasAccess(checkMemberAccess(userId))
                .role(user.getRole())
                .requiredAccess(UserRole.MEMBER.name())
                .build();
    }

    private UserRole getUserRoleEnum(Long userId) {
        User user = findUser(userId);
        if (!UserRole.isValid(user.getRole())) {
            throw new InvalidRoleException("User has an invalid role: " + user.getRole());
        }
        return UserRole.fromString(user.getRole());
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
    }

    private RoleResponse mapToRoleResponse(User user) {
        return RoleResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
