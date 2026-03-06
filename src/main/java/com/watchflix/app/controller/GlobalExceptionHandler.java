package com.watchflix.app.controller;

import com.watchflix.app.domain.dto.response.ErrorResponseDto;
import com.watchflix.app.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFoundException (UserNotFoundException ex)
    {
        ErrorResponseDto error = new ErrorResponseDto(ex.getMessage());

        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleMovieNotFoundException (MovieNotFoundException ex){
        ErrorResponseDto error = new ErrorResponseDto(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(MovieNotWatchedException.class)
    public ResponseEntity<ErrorResponseDto> handleMovieNotWatchedException(MovieNotWatchedException ex){
        ErrorResponseDto error = new ErrorResponseDto(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MovieAlreadyAddedException.class)
    public ResponseEntity<ErrorResponseDto> handleMovieAlreadyAddedException(MovieAlreadyAddedException ex)
    {
        ErrorResponseDto error = new ErrorResponseDto(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);

    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(
            MethodArgumentNotValidException ex) {

        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(e -> e.getDefaultMessage())
                .orElse("Validation failed");

        ErrorResponseDto error = new ErrorResponseDto(errorMessage);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ErrorResponseDto> handleDuplicateUserException(
            DuplicateUserException ex) {
        ErrorResponseDto error = new ErrorResponseDto(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}
