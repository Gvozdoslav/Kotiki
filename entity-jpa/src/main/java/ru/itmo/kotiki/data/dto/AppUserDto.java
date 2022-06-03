package ru.itmo.kotiki.data.dto;

import ru.itmo.kotiki.data.model.AppUser;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class AppUserDto implements Serializable {
    private final UUID id;
    private final String name;
    private final String username;
    private final String password;
    private final List<RoleDto> roles;

    public AppUserDto(UUID id, String name, String username, String password, List<RoleDto> roles) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public static AppUserDto convertToAppUserDto(AppUser appUser) {
        return new AppUserDto(appUser.getId(),
                appUser.getName(),
                appUser.getUsername(),
                appUser.getPassword(),
                appUser.getRoles().stream().map(RoleDto::convertToRoleDto).toList());
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<RoleDto> getRoles() {
        return roles;
    }

    public AppUser convertToAppUser() {

        var user = new AppUser();

        if (id != null) user.setId(id);

        if (name != null) user.setName(name);

        if (username != null) user.setUsername(username);

        if (password != null) user.setPassword(password);

        if (roles != null) user.setRoles(roles.stream().map(RoleDto::convertToRole).toList());

        return user;
    }
}
