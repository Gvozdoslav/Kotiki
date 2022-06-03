package ru.itmo.kotiki.data.tool;

import java.util.UUID;

public class OwnerNotFoundException extends RuntimeException {

    public OwnerNotFoundException(UUID id) {
        super("Owner hasn't found: " + id);
    }
}
