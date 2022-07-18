package ru.itmo.kotiki.foreign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import ru.itmo.kotiki.data.dto.AddRoleDto;
import ru.itmo.kotiki.data.dto.AppUserDto;
import ru.itmo.kotiki.data.dto.RoleDto;
import ru.itmo.kotiki.data.model.AppUser;
import ru.itmo.kotiki.data.model.Role;
import ru.itmo.kotiki.foreign.service.AppUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static ru.itmo.kotiki.foreign.util.TokenProcessor.refreshTokens;
import static ru.itmo.kotiki.foreign.util.TokenProcessor.unsuccessfulProcessingTokens;

@RestController
@RequestMapping("/users")
@EnableMethodSecurity(securedEnabled = true)
public class AppUserController {

    private final AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserService appUserService) {

        this.appUserService = appUserService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CollectionModel<EntityModel<AppUser>>> getUsers() {
        try {
            CollectionModel<EntityModel<AppUser>> users = appUserService.findAll();

            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getbyusername/{username}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<EntityModel<AppUser>> getUser(@PathVariable String username) {
        try {
            EntityModel<AppUser> user = appUserService.findUserByUsername(username);

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/save/user")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<EntityModel<AppUser>> saveUser(@RequestBody AppUserDto appUserDto) {

        try {

            Logger.getAnonymousLogger().log(Level.INFO, "ABOBKENS");
            EntityModel<AppUser> userEntityModel = appUserService.saveUser(appUserDto.convertToAppUser());
            return ResponseEntity
                    .created(userEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                    .body(userEntityModel);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/save/role")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<EntityModel<Role>> saveRole(@RequestBody RoleDto roleResource) {

        try {

            EntityModel<Role> roleEntityModel = appUserService.saveRole(roleResource.convertToRole());
            return ResponseEntity
                    .created(roleEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                    .body(roleEntityModel);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/save/addrole")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> addRoleToUser(@RequestBody AddRoleDto addRoleDto) {

        try {

            appUserService.addRoleToUser(addRoleDto.getUsername(), addRoleDto.getRole().getName());
            return ResponseEntity.ok().build();
        } catch (Exception e) {

            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/token/refresh")
    @PreAuthorize("hasAuthority('USER')")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

            try {

                refreshTokens(request, response, appUserService);
            } catch (Exception e) {

                unsuccessfulProcessingTokens(request, response, e);
            }
            return;
        }

        throw new RuntimeException("Refresh token is missing");
    }
}
