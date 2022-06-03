package ru.itmo.kotiki.data.dto;

import java.io.Serializable;
import java.util.UUID;

public class OwnerAndIdDto implements Serializable {
    private final OwnerDto ownerDto;
    private final UUID id;


    public OwnerAndIdDto(OwnerDto ownerDto, UUID id) {
        this.ownerDto = ownerDto;
        this.id = id;
    }

    public OwnerDto getOwnerDto() {
        return ownerDto;
    }

    public UUID getId() {
        return id;
    }
}
