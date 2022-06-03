package ru.itmo.kotiki.data.dto;

import ru.itmo.kotiki.data.enums.AppUserRole;
import ru.itmo.kotiki.data.model.Role;

import java.io.Serializable;
import java.util.UUID;

public class RoleDto implements Serializable {

    private final UUID id;
    private final AppUserRole name;

    public RoleDto(UUID id, AppUserRole name) {
        this.id = id;
        this.name = name;
    }

    public static RoleDto convertToRoleDto(Role role) {
        return new RoleDto(role.getId(), role.getRoleName());
    }

    public UUID getId() {
        return id;
    }

    public AppUserRole getName() {
        return name;
    }

    public Role convertToRole() {

        var role = new Role();

        if (id != null) role.setId(id);

        if (name != null) role.setRoleName(name);

        return role;
    }
}
