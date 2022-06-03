package ru.itmo.kotiki.data.dto;

import ru.itmo.kotiki.data.enums.CatColor;

import java.io.Serializable;

public class CatColorAndUsernameDto implements Serializable {

    private final CatColor catColor;
    private final String username;

    public CatColorAndUsernameDto(CatColor catColor, String username) {
        this.catColor = catColor;
        this.username = username;
    }

    public CatColor getCatColor() {
        return catColor;
    }

    public String getUsername() {
        return username;
    }
}
