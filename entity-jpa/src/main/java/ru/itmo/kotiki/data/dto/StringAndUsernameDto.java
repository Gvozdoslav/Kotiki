package ru.itmo.kotiki.data.dto;

import java.io.Serializable;

public class StringAndUsernameDto implements Serializable {

    private final String string;
    private final String username;

    public StringAndUsernameDto(String string, String username) {
        this.string = string;
        this.username = username;
    }

    public String getString() {
        return string;
    }

    public String getUsername() {
        return username;
    }
}
