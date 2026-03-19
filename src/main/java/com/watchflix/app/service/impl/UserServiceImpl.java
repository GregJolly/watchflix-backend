package com.watchflix.app.service.impl;

import com.watchflix.app.domain.AddMovieRequest;
import com.watchflix.app.domain.MovieStatus;
import com.watchflix.app.domain.RateMovieRequest;
import com.watchflix.app.domain.entity.User;
import com.watchflix.app.domain.entity.UserMovie;
import com.watchflix.app.exception.MovieAlreadyAddedException;
import com.watchflix.app.exception.MovieNotFoundException;
import com.watchflix.app.exception.MovieNotWatchedException;
import com.watchflix.app.exception.UserNotFoundException;
import com.watchflix.app.repository.UserMovieRepository;
import com.watchflix.app.repository.UserRepository;
import com.watchflix.app.service.MovieService;
import com.watchflix.app.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMovieRepository userMovieRepository;
    private final MovieService movieService;

    public UserServiceImpl(UserRepository userRepository, UserMovieRepository userMovieRepository, MovieService movieService) {
        this.userRepository = userRepository;
        this.movieService = movieService;
        this.userMovieRepository = userMovieRepository;
    }

    @Override
    public User getUserByUsername(String username){

        return userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public List<UserMovie> listUserMovies(UUID userId){

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        return userMovieRepository.findByUser(user);
    }

    @Override
    public List<User> searchUser(String query) {
        return userRepository.findByUsernameContainingIgnoreCase(query);
    }

    @Override
    public UserMovie addMovie(UUID userId, AddMovieRequest request) {
        Instant now = Instant.now();
        User user =  userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        if (userMovieRepository.existsByUserAndTmdbId(user, request.tmdbId())) {
            throw new MovieAlreadyAddedException(request.tmdbId());
        }
        UserMovie userMovie = new UserMovie();
        userMovie.setUser(user);
        userMovie.setTmdbId(request.tmdbId());
        userMovie.setStatus(request.status());
        userMovie.setFavorite(false);
        userMovie.setTitle(request.title());
        userMovie.setPosterPath(request.posterPath());
        userMovie.setReleaseDate(request.releaseDate());
        userMovie.setCreatedAt(Instant.now());
        userMovie.setUpdatedAt(Instant.now());

        return userMovieRepository.save(userMovie);
    }

    @Override
    public UserMovie rateMovie(UUID userId, Integer tmdbId, RateMovieRequest request) {
        User user =  userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        UserMovie userMovie = userMovieRepository.findByUserAndTmdbId(user, tmdbId)
                .orElseThrow(() -> new MovieNotFoundException(tmdbId));
        Instant now = Instant.now();
        if (userMovie.getStatus() != MovieStatus.WATCHED) {
            throw new MovieNotWatchedException(tmdbId);
        }
        userMovie.setRating(request.rating());
        userMovie.setUpdatedAt(now);

        return userMovieRepository.save(userMovie);

    }
    @Transactional
    @Override
    public void removeUserMovie(UUID userId, Integer tmdbId) {
        User user =  userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        UserMovie userMovie = userMovieRepository.findByUserAndTmdbId(user, tmdbId)
                .orElseThrow(() -> new MovieNotFoundException(tmdbId));
        userMovieRepository.delete(userMovie);
    }

    @Override
    public UserMovie toggleWatchList(UUID userId, Integer tmdbId) {
        Instant now = Instant.now();
        User user =  userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        UserMovie userMovie = userMovieRepository.findByUserAndTmdbId(user, tmdbId)
                .orElseThrow(() -> new MovieNotFoundException(tmdbId));

        if(userMovie.getStatus() == MovieStatus.WATCHED){
            userMovie.setStatus(MovieStatus.WATCHLIST);
            userMovie.setRating(null);

        }
        else
        {
            userMovie.setStatus(MovieStatus.WATCHED);
        }

        userMovie.setUpdatedAt(now);
        return userMovieRepository.save(userMovie);
    }

    @Override
    public UserMovie toggleFavorite(UUID userId, Integer tmdbId) {
        Instant now = Instant.now();
        User user =  userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        UserMovie userMovie = userMovieRepository.findByUserAndTmdbId(user, tmdbId)
                .orElseThrow(() -> new MovieNotFoundException(tmdbId));
        userMovie.setFavorite(!userMovie.getFavorite());

        userMovie.setUpdatedAt(now);
        return userMovieRepository.save(userMovie);
    }
}

