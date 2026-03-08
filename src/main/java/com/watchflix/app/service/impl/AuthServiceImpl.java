package com.watchflix.app.service.impl;

import com.watchflix.app.domain.LoginRequest;
import com.watchflix.app.domain.RegisterRequest;
import com.watchflix.app.domain.entity.User;
import com.watchflix.app.exception.DuplicateUserException;
import com.watchflix.app.exception.InvalidCredentialException;
import com.watchflix.app.exception.UserNotFoundException;
import com.watchflix.app.repository.UserRepository;
import com.watchflix.app.security.JwtService;
import com.watchflix.app.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuthServiceImpl  implements AuthService  {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;


    public AuthServiceImpl(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder){

        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public String login(LoginRequest request) throws UserNotFoundException{

        User user = userRepository.findByUsername(request.username()).orElseThrow(UserNotFoundException::new);

        if ( passwordEncoder.matches(request.password(), user.getPassword()))
        {
            return jwtService.generateToken(request.username());
        }
        else {
            throw new InvalidCredentialException();
        }


    }

    @Override
    public String register(RegisterRequest request){

        Instant now = Instant.now();
        boolean doesUserExist = userRepository.existsByUsername(request.username());

        if(doesUserExist){
           throw new DuplicateUserException(request.username());
        }
    // create a user


        String hashedPassword = passwordEncoder.encode(request.password());
        User user = new User(
                null,
                request.username(),
                hashedPassword,
                now,
                now
        );


        User savedUser = userRepository.save(user);

        return jwtService.generateToken(
                request.username()
        );



    }
}
