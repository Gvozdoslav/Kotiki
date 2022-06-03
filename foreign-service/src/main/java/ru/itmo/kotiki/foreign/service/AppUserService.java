package ru.itmo.kotiki.foreign.service;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import ru.itmo.kotiki.data.enums.AppUserRole;
import ru.itmo.kotiki.data.model.AppUser;
import ru.itmo.kotiki.data.model.Role;

public interface AppUserService {

    EntityModel<AppUser> saveUser(AppUser user);

    EntityModel<Role> saveRole(Role role);

    void addRoleToUser(String username, AppUserRole userRole);

    EntityModel<AppUser> findUserByUsername(String username);

    CollectionModel<EntityModel<AppUser>> findAll();
}
