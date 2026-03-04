package com.watchflix.app.service;

import com.watchflix.app.domain.AddMovieRequest;
import com.watchflix.app.domain.RateMovieRequest;
import com.watchflix.app.domain.entity.User;
import com.watchflix.app.domain.entity.UserMovie;

import java.util.List;
import java.util.UUID;

public interface UserService {

    //profile actions
    UserMovie addMovie(UUID userId, AddMovieRequest request);
    UserMovie toggleWatchList( UUID userId, Integer tmdbId);
    UserMovie toggleFavorite(UUID userId, Integer tmdbId);
    UserMovie rateMovie( UUID userId, Integer tmdbId, RateMovieRequest request);
    void removeUserMovie (UUID userId, Integer tmdbId);
    List<UserMovie> listUserMovies(UUID userId);

    //search tab actions for users
    User getUserByUsername(String username);
    List<User> searchUser(String query);

}
