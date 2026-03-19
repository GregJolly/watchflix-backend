package com.watchflix.app.domain.dto.request;

import com.watchflix.app.domain.MovieStatus;
import jakarta.validation.constraints.NotNull;

public record AddMovieRequestDto(
        @NotNull
        Integer tmdbId,
        @NotNull
        MovieStatus status,
        String title,
        String posterPath,
        String releaseDate

) {
}
