package ru.itmo.kotiki.foreign.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import ru.itmo.kotiki.data.model.Role;

@Component
public class RoleModelAssembler implements RepresentationModelAssembler<Role, EntityModel<Role>> {
    @Override
    public EntityModel<Role> toModel(Role role) {

        return EntityModel.of(role);
    }
}
