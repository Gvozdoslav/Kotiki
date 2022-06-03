package ru.itmo.kotiki.data.dto;

import ru.itmo.kotiki.data.model.Owner;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class OwnerDto implements Serializable {

    private final UUID id;
    private final LocalDate birth;
    private final String name;
    private final AppUserDto user;

    public OwnerDto(UUID id, LocalDate birth, String name, AppUserDto user) {

        this.id = id;
        this.birth = birth;
        this.name = name;
        this.user = user;
    }

    public static OwnerDto convertToOwnerDto(Owner owner) {

        AppUserDto appUserDto = new AppUserDto(owner.getUser().getId(),
                owner.getUser().getName(),
                owner.getUser().getUsername(),
                owner.getUser().getPassword(),
                null);

        return new OwnerDto(owner.getId(),
                owner.getBirth(),
                owner.getName(),
                appUserDto);
    }

    public LocalDate getBirth() {
        return birth;
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    public AppUserDto getUser() {
        return user;
    }

    public Owner convertToOwner() {

        Owner owner = new Owner();

        if (id != null) owner.setId(id);

        if (birth != null) owner.setBirth(birth);

        if (name != null) owner.setName(name);

        if (user != null) owner.setUser(user.convertToAppUser());

        return owner;
    }
}