package com.librarie.auth_api.entities;


import java.util.Set;

public enum EnumRole {
    ADMIN(Set.of("READ_PRIVILEGES", "WRITE_PRIVILEGES", "DELETE_PRIVILEGES", "MANAGE_USERS")),
    MANAGER(Set.of("READ_PRIVILEGES", "WRITE_PRIVILEGES", "MANAGE_USERS")),
    USER(Set.of("READ_PRIVILEGES"));

    private final Set<String> permissions;

    EnumRole(Set<String> permissions) {
        this.permissions = permissions;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

}
