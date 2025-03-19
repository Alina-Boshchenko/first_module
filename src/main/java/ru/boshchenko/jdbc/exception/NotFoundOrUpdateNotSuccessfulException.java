package ru.boshchenko.jdbc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundOrUpdateNotSuccessfulException extends RuntimeException{
    public NotFoundOrUpdateNotSuccessfulException(String message) {
        super(message);
    }
}
