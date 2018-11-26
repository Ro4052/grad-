package com.scottlogic.librarygradproject.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BookIsAvailableException extends RuntimeException {

    public BookIsAvailableException(long bookId) {
        super("Cannot reserve book " + bookId + " as it is currently available");
    }
}
