package ru.itmo.kotiki.data.tool;

import java.util.UUID;

public class CatNotFoundException extends RuntimeException {

    public CatNotFoundException(UUID id) {
        super("Cat hasn't found: " + id);
    }
}
