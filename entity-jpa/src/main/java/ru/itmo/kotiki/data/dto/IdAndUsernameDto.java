package ru.itmo.kotiki.data.dto;

import java.io.Serializable;
import java.util.UUID;

public class IdAndUsernameDto implements Serializable {

    private final UUID id;
    private final String username;

    public IdAndUsernameDto(UUID id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public UUID getId() {
        return id;
    }
}
