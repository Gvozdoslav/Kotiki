package ru.itmo.kotiki.foreign.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
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

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static ru.itmo.kotiki.foreign.util.TokenProcessor.refreshTokens;
import static ru.itmo.kotiki.foreign.util.TokenProcessor.unsuccessfulProcessingTokens;

@RestController
@RequestMapping("/users")
public class AppUserController {

    private final AppUserService appUserService;
    private final AmqpTemplate template;

    @Autowired
    public AppUserController(AppUserService appUserService, AmqpTemplate template) {

        this.appUserService = appUserService;
        this.template = template;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<AppUser>>> getUsers(@RequestBody String message) {
        try {
            template.convertAndSend("userQueue", message);
            CollectionModel<EntityModel<AppUser>> users = appUserService.findAll();

            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getbyusername/{username}")
    public ResponseEntity<EntityModel<AppUser>> getUser(@PathVariable String username) {
        try {
            EntityModel<AppUser> user = appUserService.findUserByUsername(username);

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/save/user")
    public ResponseEntity<EntityModel<AppUser>> saveUser(@RequestBody AppUserDto appUserDto) {

        try {

            EntityModel<AppUser> userEntityModel = appUserService.saveUser(appUserDto.convertToAppUser());
            return ResponseEntity
                    .created(userEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                    .body(userEntityModel);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/save/role")
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
    public ResponseEntity<?> addRoleToUser(@RequestBody AddRoleDto addRoleDto) {

        try {

            appUserService.addRoleToUser(addRoleDto.getUsername(), addRoleDto.getRole().getName());
            return ResponseEntity.ok().build();
        } catch (Exception e) {

            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/token/refresh")
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
