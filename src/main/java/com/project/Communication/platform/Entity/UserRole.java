package com.project.communication.platform.entity;

public enum UserRole {
    ADMIN,
    MODERATOR,
    MEMBER;

    public static boolean isValid(String role) {
        if (role == null || role.isBlank()) {
            return false;
        }
        try {
            valueOf(role.trim().toUpperCase());
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    public static UserRole fromString(String role) {
        return valueOf(role.trim().toUpperCase());
    }
}
