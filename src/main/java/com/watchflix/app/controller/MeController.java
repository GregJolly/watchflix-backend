package com.watchflix.app.controller;

import com.watchflix.app.domain.AddMovieRequest;
import com.watchflix.app.domain.RateMovieRequest;
import com.watchflix.app.domain.dto.request.AddMovieRequestDto;
import com.watchflix.app.domain.dto.request.RateMovieRequestDto;
import com.watchflix.app.domain.dto.response.UserDto;
import com.watchflix.app.domain.dto.response.UserMovieDto;
import com.watchflix.app.domain.entity.User;
import com.watchflix.app.domain.entity.UserMovie;
import com.watchflix.app.mapper.UserMapper;
import com.watchflix.app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
            @AuthenticationPrincipal String username,
            @Valid @RequestBody AddMovieRequestDto dto)//temp
    {
        AddMovieRequest request = userMapper.fromDto(dto );
        User user = userService.getUserByUsername(username);
        UUID userId = user.getId();
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
    public ResponseEntity<UserMovieDto> toggleWatchList(@AuthenticationPrincipal String username, @PathVariable Integer tmdbId){

        User user = userService.getUserByUsername(username);
        UUID userId = user.getId();
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
    public ResponseEntity<UserMovieDto> toggleFavorite(@AuthenticationPrincipal String username, @PathVariable Integer tmdbId){

        User user = userService.getUserByUsername(username);
        UUID userId = user.getId();
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
    public ResponseEntity<UserMovieDto> rateMovie(@AuthenticationPrincipal String username, @PathVariable Integer tmdbId, @Valid @RequestBody RateMovieRequestDto dto){
        User user = userService.getUserByUsername(username);
        UUID userId = user.getId();
        RateMovieRequest request = userMapper.fromDto(dto);
        UserMovie userMovie = userService.rateMovie(userId, tmdbId, request);
        UserMovieDto userMovieDto = new UserMovieDto(
                tmdbId,
                userMovie.getTitle(),          // title — Phase 2
                userMovie.getPosterPath(),           // posterPath — Phase 2
                userMovie.getReleaseDate(),           // releaseDate — Phase 2
                userMovie.getStatus(),
                userMovie.getFavorite(),
                request.rating()
        );
        return new ResponseEntity<>(userMovieDto, HttpStatus.OK);

    }

    @DeleteMapping(path = "/movies/{tmdbId}")
    public ResponseEntity<Void> deleteUserMovie(@AuthenticationPrincipal String username, @PathVariable Integer tmdbId)
    {
        User user = userService.getUserByUsername(username);
        UUID userId = user.getId();
        userService.removeUserMovie(userId, tmdbId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/movies")
    public ResponseEntity<List<UserMovieDto>> listUserMovies(@AuthenticationPrincipal String username){
        User user = userService.getUserByUsername(username);
        UUID userId = user.getId();
        List<UserMovie> userMovies = userService.listUserMovies(userId);
        return new ResponseEntity<>(userMovies.stream().map(movie -> new UserMovieDto(
                        movie.getTmdbId(),
                        movie.getTitle(), movie.getPosterPath(), movie.getReleaseDate(),
                        movie.getStatus(),
                        movie.getFavorite(),
                        movie.getRating()
                ))
                .toList(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<UserDto> getMe(@AuthenticationPrincipal  String username)
    {
        User user = userService.getUserByUsername(username);
        return new ResponseEntity<>(userMapper.toDto(user), HttpStatus.FOUND);
    }



}
