package com.watchflix.app.repository;

import com.watchflix.app.domain.entity.User;
import com.watchflix.app.domain.entity.UserMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserMovieRepository extends JpaRepository<UserMovie, UUID> {

    List<UserMovie> findByUser(User user);
    Optional<UserMovie> findByUserAndTmdbId(User user, Integer tmdbId);
    boolean existsByUserAndTmdbId(User user, Integer tmbdId);


}
