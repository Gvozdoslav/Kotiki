package ru.itmo.kotiki.data.tool;

public class AppUserNotFoundException extends RuntimeException {

    public AppUserNotFoundException(String username) {
        super("User hasn't found: " + username);
    }
}
