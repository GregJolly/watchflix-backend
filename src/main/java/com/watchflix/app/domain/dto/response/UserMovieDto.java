package com.watchflix.app.domain.dto.response;

import com.watchflix.app.domain.MovieStatus;

public record UserMovieDto(Integer tmdbId, String title, String posterPath, String releaseDate, MovieStatus status, Boolean favourite, Integer rating ) {
}
