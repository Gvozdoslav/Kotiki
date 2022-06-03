package ru.itmo.kotiki.foreign.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.kotiki.data.enums.AppUserRole;
import ru.itmo.kotiki.data.model.AppUser;
import ru.itmo.kotiki.data.model.Role;
import ru.itmo.kotiki.data.tool.AppUserNotFoundException;
import ru.itmo.kotiki.data.tool.RoleNotFoundException;
import ru.itmo.kotiki.foreign.assembler.AppUserModelAssembler;
import ru.itmo.kotiki.foreign.assembler.RoleModelAssembler;
import ru.itmo.kotiki.foreign.repository.AppUserRepository;
import ru.itmo.kotiki.foreign.repository.RoleRepository;
import ru.itmo.kotiki.foreign.service.AppUserService;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@Transactional
public class AppUserServiceImpl implements AppUserService, UserDetailsService {

    private final AppUserModelAssembler appUserModelAssembler = new AppUserModelAssembler();
    private final RoleModelAssembler roleModelAssembler = new RoleModelAssembler();
    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppUserServiceImpl(AppUserRepository appUserRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {

        this.appUserRepository = appUserRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public EntityModel<AppUser> saveUser(AppUser user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return appUserModelAssembler.toModel(appUserRepository.save(user));
    }

    @Override
    public EntityModel<Role> saveRole(Role role) {

        return roleModelAssembler.toModel(roleRepository.save(role));
    }

    @Override
    @Transactional
    public void addRoleToUser(String username, AppUserRole userRole) {

        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new AppUserNotFoundException(username));
        Role role = roleRepository.findByRoleName(userRole)
                .orElseThrow(() -> new RoleNotFoundException(userRole));

        user.getRoles().add(role);
    }

    @Override
    public EntityModel<AppUser> findUserByUsername(String username) {

        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new AppUserNotFoundException(username));

        return appUserModelAssembler.toModel(user);
    }

    @Override
    public CollectionModel<EntityModel<AppUser>> findAll() {
        List<EntityModel<AppUser>> users = appUserRepository.findAll()
                .stream().map(appUserModelAssembler::toModel)
                .toList();

        return CollectionModel.of(users,
                linkTo(methodOn(AppUserServiceImpl.class).findAll()).withSelfRel());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser user = appUserRepository.findByUsername(username).orElse(null);
        if (user == null)
            throw new UsernameNotFoundException("Username not found in db");

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role ->
                authorities.add(
                        new SimpleGrantedAuthority(role.getRoleName().toString())));

        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
