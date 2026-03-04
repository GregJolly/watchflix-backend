package com.watchflix.app.exception;

import java.util.UUID;

public class MovieAlreadyAddedException extends RuntimeException{

    private final Integer tmdbId;

    public MovieAlreadyAddedException(Integer id) {

        super(String.format("Movie with the id '%s' has already been added", id ));
        this.tmdbId = id;

    }

    public Integer getId() {
        return tmdbId;
    }
}
