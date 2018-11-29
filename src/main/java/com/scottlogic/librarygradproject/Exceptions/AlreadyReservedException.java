package com.scottlogic.librarygradproject.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyReservedException extends RuntimeException{
    public AlreadyReservedException(Long id) {
        super("You have already reserved the book with id " + id);
    }
}
