package ru.itmo.kotiki.data.tool;

import ru.itmo.kotiki.data.enums.AppUserRole;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(AppUserRole roleName) {
        super("Role hasn't found: " + roleName.toString());
    }
}
