package com.watchflix.app.mapper.impl;

import com.watchflix.app.domain.*;
import com.watchflix.app.domain.dto.request.*;
import com.watchflix.app.domain.dto.response.UserDto;
import com.watchflix.app.domain.dto.response.UserMovieDto;
import com.watchflix.app.domain.entity.User;
import com.watchflix.app.domain.entity.UserMovie;
import com.watchflix.app.domain.tmdb.TmdbMovieResponse;
import com.watchflix.app.mapper.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    //RESPONSE MAPPING
    @Override
    public UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getUsername());
    }

    @Override
    public UserMovieDto toDto(UserMovie movie, TmdbMovieResponse tmdbMovie) {
        return (new UserMovieDto(movie.getTmdbId(), tmdbMovie.getTitle(), tmdbMovie.getPosterPath(), tmdbMovie.getReleaseDate(), movie.getStatus(), movie.getFavorite(), movie.getRating() ));
    }


    //REQUEST MAPPING
    @Override
    public RegisterRequest fromDto(RegisterRequestDto dto) {
        return new RegisterRequest(dto.username(), dto.password());
    }

    @Override
    public LoginRequest fromDto(LoginRequestDto dto) {
        return new LoginRequest(dto.username(), dto.password());
    }

    @Override
    public AddMovieRequest fromDto(AddMovieRequestDto dto) {
        return new AddMovieRequest(dto.tmdbId(), dto.status());
    }

    @Override
    public RateMovieRequest fromDto(RateMovieRequestDto dto) {
        return new RateMovieRequest(dto.rating());
    }

    @Override
    public WatchMovieRequest fromDto(WatchMovieRequestDto dto) {
        return new WatchMovieRequest(dto.rating());
    }
}


