package com.librarie.auth_api.dtos;

public class AssignRoleDto {
    private Long userId;
    private String roleName;  // "ADMIN", "USER", etc.

    // Getters et Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
