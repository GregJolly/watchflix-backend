package com.watchflix.app.domain.dto.response;

import java.util.List;

public record UserProfileDto(String username, List<UserMovieDto> userMovies) {
}
