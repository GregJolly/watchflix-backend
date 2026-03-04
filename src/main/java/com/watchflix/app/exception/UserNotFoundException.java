package com.watchflix.app.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException{

    private UUID id;

    public UserNotFoundException(UUID id)
    {
        super(String.format("The user with the ID '%s' cannot be found", id));
        this.id = id;


    }

    public UUID getId() {
        return id;
    }
}
