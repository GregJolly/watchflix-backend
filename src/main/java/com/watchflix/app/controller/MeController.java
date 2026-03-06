package com.watchflix.app.controller;

import com.watchflix.app.domain.AddMovieRequest;
import com.watchflix.app.domain.RateMovieRequest;
import com.watchflix.app.domain.dto.request.AddMovieRequestDto;
import com.watchflix.app.domain.dto.request.RateMovieRequestDto;
import com.watchflix.app.domain.dto.response.UserMovieDto;
import com.watchflix.app.domain.entity.UserMovie;
import com.watchflix.app.domain.tmdb.TmdbMovieResponse;
import com.watchflix.app.mapper.UserMapper;
import com.watchflix.app.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping(path = "/api/v1/me")
public class MeController {

    private final UserService userService;
    private final UserMapper userMapper;


    public MeController(UserService userService, UserMapper userMapper){
        this.userService = userService;
        this.userMapper = userMapper;


    }

    @PostMapping(path = "/movies")
    public ResponseEntity<UserMovieDto> addMovies(
            @RequestParam UUID userId,
            @Valid @RequestBody AddMovieRequestDto dto)//temp
    {
        AddMovieRequest request = userMapper.fromDto(dto );
        UserMovie userMovie = userService.addMovie(userId, request);
        UserMovieDto userMovieDto = new UserMovieDto(
                userMovie.getTmdbId(),
                null,           // title — Phase 2
                null,           // posterPath — Phase 2
                null,           // releaseDate — Phase 2
                userMovie.getStatus(),
                userMovie.getFavorite(),
                userMovie.getRating()
        );

        return new ResponseEntity<>(userMovieDto, HttpStatus.CREATED);
    }

    @PatchMapping(path = "/movies/{tmdbId}/watch")
    public ResponseEntity<UserMovieDto> toggleWatchList(@RequestParam UUID userId, @PathVariable Integer tmdbId){

        UserMovie userMovie = userService.toggleWatchList(userId, tmdbId);
        UserMovieDto userMovieDto = new UserMovieDto(
                tmdbId,
                null,           // title — Phase 2
                null,           // posterPath — Phase 2
                null,           // releaseDate — Phase 2
                userMovie.getStatus(),
                userMovie.getFavorite(),
                userMovie.getRating()
        );
        return new ResponseEntity<>(userMovieDto, HttpStatus.OK);

    }

    @PatchMapping(path = "/movies/{tmdbId}/favorite")
    public ResponseEntity<UserMovieDto> toggleFavorite(@RequestParam UUID userId, @PathVariable Integer tmdbId){

        UserMovie userMovie = userService.toggleFavorite(userId, tmdbId);
        UserMovieDto userMovieDto = new UserMovieDto(
                tmdbId,
                null,           // title — Phase 2
                null,           // posterPath — Phase 2
                null,           // releaseDate — Phase 2
                userMovie.getStatus(),
                userMovie.getFavorite(),
                userMovie.getRating()
        );
        return new ResponseEntity<>(userMovieDto, HttpStatus.OK);

    }
    @PatchMapping(path = "/movies/{tmdbId}/rate")
    public ResponseEntity<UserMovieDto> rateMovie(@RequestParam UUID userId, @PathVariable Integer tmdbId, @Valid @RequestBody RateMovieRequestDto dto){
        RateMovieRequest request = userMapper.fromDto(dto);
        UserMovie userMovie = userService.rateMovie(userId, tmdbId, request);
        UserMovieDto userMovieDto = new UserMovieDto(
                tmdbId,
                null,           // title — Phase 2
                null,           // posterPath — Phase 2
                null,           // releaseDate — Phase 2
                userMovie.getStatus(),
                userMovie.getFavorite(),
                request.rating()
        );
        return new ResponseEntity<>(userMovieDto, HttpStatus.OK);

    }

    @DeleteMapping(path = "/movies/{tmdbId}/delete")
    public ResponseEntity<Void> deleteUserMovie(@RequestParam UUID userId, @PathVariable Integer tmdbId)
    {
        userService.removeUserMovie(userId, tmdbId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/movies")
    public ResponseEntity<List<UserMovieDto>> listUserMovies(@RequestParam UUID userId){
        List<UserMovie> userMovies = userService.listUserMovies(userId);
        return new ResponseEntity<>(userMovies.stream().map(movie -> new UserMovieDto(
                        movie.getTmdbId(),
                        null, null, null,
                        movie.getStatus(),
                        movie.getFavorite(),
                        movie.getRating()
                ))
                .toList(), HttpStatus.OK);
    }


}
