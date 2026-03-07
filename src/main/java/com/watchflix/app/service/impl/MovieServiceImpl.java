package com.watchflix.app.service.impl;

import com.watchflix.app.domain.dto.response.MovieDetailDto;
import com.watchflix.app.domain.dto.response.MovieSummaryDto;
import com.watchflix.app.domain.tmdb.TmdbFilmResponse;
import com.watchflix.app.domain.tmdb.TmdbMovieResponse;
import com.watchflix.app.mapper.MovieMapper;
import com.watchflix.app.service.MovieService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieMapper movieMapper;
    private final RestTemplate restTemplate;

    @Value("${tmdb.api.key}")
    private String apiKey;

    @Value("${tmdb.base.url}")
    private String baseUrl;

    public MovieServiceImpl(MovieMapper movieMapper, RestTemplate restTemplate){

        this.movieMapper = movieMapper;
        this.restTemplate = restTemplate;

    }

    @Override
    public List<MovieSummaryDto> getPopularMovies() {

        String url = baseUrl + "/movie/popular?api_key=" + apiKey;
        TmdbFilmResponse response = restTemplate.getForObject(url, TmdbFilmResponse.class);
        List<TmdbMovieResponse> movies = response.getResults();
        return movies.stream().map(movieMapper::toSummaryDto).toList();

    }

    @Override
    public List<MovieSummaryDto> searchMovies(String query) {

        String url = baseUrl + "/search/movie?api_key=" + apiKey + "&query=" + query;
        TmdbFilmResponse response = restTemplate.getForObject(url, TmdbFilmResponse.class);
        List<TmdbMovieResponse> movies = response.getResults();
        return movies.stream().map(movieMapper::toSummaryDto).toList();

    }

    @Override
    public MovieDetailDto getMoviebyId(Integer id) {

        String url = baseUrl + "/movie/"+ id + "?api_key=" + apiKey;
        TmdbMovieResponse movie = restTemplate.getForObject(url, TmdbMovieResponse.class);
        return movieMapper.toDetailDto(movie);

    }
}
