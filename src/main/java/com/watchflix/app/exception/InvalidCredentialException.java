package com.watchflix.app.exception;

public class InvalidCredentialException extends RuntimeException{



    public InvalidCredentialException()
    {
        super("Incorrect Username/Password");

    }

}

