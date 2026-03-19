package com.watchflix.app.repository;

import com.watchflix.app.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);
    List<User> findByUsernameContainingIgnoreCase(String query);
    boolean existsByUsername(String username);

}
