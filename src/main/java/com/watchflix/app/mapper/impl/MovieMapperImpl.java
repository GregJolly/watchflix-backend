package com.watchflix.app.mapper.impl;

import com.watchflix.app.domain.dto.response.MovieDetailDto;
import com.watchflix.app.domain.dto.response.MovieSummaryDto;
import com.watchflix.app.domain.tmdb.TmdbMovieResponse;
import com.watchflix.app.mapper.MovieMapper;
import org.springframework.stereotype.Component;

@Component
public class MovieMapperImpl implements MovieMapper {

    @Override
    public MovieDetailDto toDetailDto(TmdbMovieResponse tmdb) {
        return new MovieDetailDto(tmdb.getId(), tmdb.getTitle(), tmdb.getReleaseDate(), tmdb.getPosterPath(), tmdb.getOverview(), tmdb.getDirector(), tmdb.getCast());
    }

    @Override
    public MovieSummaryDto toSummaryDto(TmdbMovieResponse tmdb) {
        return new MovieSummaryDto(tmdb.getId(), tmdb.getTitle(), tmdb.getReleaseDate(), tmdb.getPosterPath());
    }
}
