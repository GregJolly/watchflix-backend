package com.watchflix.app.exception;

import java.util.UUID;

public class MovieNotFoundException extends RuntimeException{

    private final Integer tmdbId;

    public MovieNotFoundException(Integer id) {

        super(String.format("Movie with the id '%s' cannot be found, ", id ));
        this.tmdbId = id;

    }

    public Integer getId() {
        return tmdbId;
    }
}
