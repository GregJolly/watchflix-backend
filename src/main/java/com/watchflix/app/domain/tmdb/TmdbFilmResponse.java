package com.watchflix.app.domain.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TmdbFilmResponse {
    @JsonProperty("results")
    private List<TmdbMovieResponse>  results;

    public List<TmdbMovieResponse> getResults() {
        return results;
    }

    public void setResults(List<TmdbMovieResponse> results) {
        this.results = results;
    }
}
