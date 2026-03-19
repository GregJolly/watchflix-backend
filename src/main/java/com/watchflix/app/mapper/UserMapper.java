package com.watchflix.app.mapper;

import com.watchflix.app.domain.*;
import com.watchflix.app.domain.dto.request.*;
import com.watchflix.app.domain.dto.response.AuthResponseDto;
import com.watchflix.app.domain.dto.response.UserDto;
import com.watchflix.app.domain.dto.response.UserMovieDto;
import com.watchflix.app.domain.dto.response.UserProfileDto;
import com.watchflix.app.domain.entity.User;
import com.watchflix.app.domain.entity.UserMovie;
import com.watchflix.app.domain.tmdb.TmdbMovieResponse;

public interface UserMapper {
    //RequestMapping
    RegisterRequest fromDto(RegisterRequestDto dto);
    LoginRequest fromDto(LoginRequestDto dto);
    AddMovieRequest fromDto(AddMovieRequestDto dto);
    WatchMovieRequest fromDto(WatchMovieRequestDto dto);
    RateMovieRequest fromDto(RateMovieRequestDto dto);

    //Response Mapping
    UserDto toDto (User user);
    UserMovieDto toDto(UserMovie movie, TmdbMovieResponse tmdbMovie);
}
