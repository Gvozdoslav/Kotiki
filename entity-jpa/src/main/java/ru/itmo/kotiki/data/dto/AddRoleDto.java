package ru.itmo.kotiki.data.dto;

import java.io.Serializable;

public class AddRoleDto implements Serializable {

    private final String username;
    private final RoleDto role;

    public AddRoleDto(String username, RoleDto userRole) {
        this.username = username;
        this.role = userRole;
    }

    public String getUsername() {
        return username;
    }

    public RoleDto getRole() {
        return role;
    }
}
