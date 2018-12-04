package com.scottlogic.librarygradproject.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IncorrectBookFormatException extends RuntimeException {

    public IncorrectBookFormatException() {
        super("Incorrect book format provided");
    }
}
