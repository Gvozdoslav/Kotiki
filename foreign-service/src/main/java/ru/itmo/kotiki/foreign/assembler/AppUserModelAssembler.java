package ru.itmo.kotiki.foreign.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;
import ru.itmo.kotiki.data.model.AppUser;
import ru.itmo.kotiki.foreign.service.implementation.AppUserServiceImpl;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AppUserModelAssembler implements RepresentationModelAssembler<AppUser, EntityModel<AppUser>> {
    @Override
    public EntityModel<AppUser> toModel(AppUser user) {

        return EntityModel.of(user,
                WebMvcLinkBuilder.linkTo(methodOn(AppUserServiceImpl.class).findUserByUsername(user.getUsername())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(methodOn(AppUserServiceImpl.class).findAll()).withRel("users"));
    }
}
