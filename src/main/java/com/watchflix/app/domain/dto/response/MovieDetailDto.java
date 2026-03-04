package com.watchflix.app.domain.dto.response;

import java.util.List;

public record MovieDetailDto(Integer id, String title, String releaseDate, String posterPath, String overview, String director, List<String> cast) {
}
