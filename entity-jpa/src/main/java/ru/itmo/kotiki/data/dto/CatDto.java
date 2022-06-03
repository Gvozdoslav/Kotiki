package ru.itmo.kotiki.data.dto;

import ru.itmo.kotiki.data.enums.CatColor;
import ru.itmo.kotiki.data.model.Cat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class CatDto implements Serializable {
    private final UUID id;
    private final LocalDate birth;
    private final String breed;
    private final CatColor color;
    private final String name;
    private final OwnerDto owner;

    public CatDto(
            UUID id, LocalDate birth,
            String breed,
            CatColor color,
            String name,
            OwnerDto owner) {

        this.id = id;
        this.birth = birth;
        this.breed = breed;
        this.color = color;
        this.name = name;
        this.owner = owner;
    }

    public static CatDto convertToCatDto(Cat cat) {

        OwnerDto ownerDto = new OwnerDto(cat.getOwner().getId(),
                cat.getOwner().getBirth(),
                cat.getOwner().getName(),
                null);
        return new CatDto(cat.getId(),
                cat.getBirth(),
                cat.getBreed(),
                cat.getColor(),
                cat.getName(),
                ownerDto);
    }

    public LocalDate getBirth() {
        return birth;
    }

    public String getBreed() {
        return breed;
    }

    public CatColor getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public OwnerDto getOwner() {
        return owner;
    }

    public UUID getId() {
        return id;
    }

    public Cat convertToCat() {

        var cat = new Cat();

        if (id != null) cat.setId(id);

        if (birth != null) cat.setBirth(birth);

        if (color != null) cat.setColor(color);

        if (breed != null) cat.setBreed(breed);

        if (name != null) cat.setName(name);

        if (owner != null) cat.setOwner(owner.convertToOwner());

        return cat;
    }
}
