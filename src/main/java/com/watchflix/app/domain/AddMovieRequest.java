package com.watchflix.app.domain;

public record AddMovieRequest(Integer tmdbId, MovieStatus status, String title, String posterPath, String releaseDate ) {
}
