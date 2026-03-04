package com.watchflix.app.exception;

import java.util.UUID;

public class MovieNotWatchedException extends RuntimeException{

    private final Integer tmdbId;

    public MovieNotWatchedException( Integer tmdbId) {

        super(String.format("Movie with the id '%s' has not been watched", tmdbId ));
        this.tmdbId = tmdbId;

    }

    public Integer getId() {
        return tmdbId;
    }
}
