package com.watchflix.app.mapper;

import com.watchflix.app.domain.dto.response.MovieDetailDto;
import com.watchflix.app.domain.dto.response.MovieSummaryDto;
import com.watchflix.app.domain.tmdb.TmdbMovieResponse;

public interface MovieMapper {

    MovieSummaryDto toSummaryDto(TmdbMovieResponse tmdbMovie);
    MovieDetailDto toDetailDto (TmdbMovieResponse tmdbMovie);
}

