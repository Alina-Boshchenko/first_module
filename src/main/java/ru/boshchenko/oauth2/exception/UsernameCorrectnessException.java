package ru.boshchenko.oauth2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UsernameCorrectnessException extends RuntimeException {
    public UsernameCorrectnessException(String message) {
        super(message);
    }
}
