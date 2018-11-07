package com.scottlogic.librarygradproject;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookNotFoundException extends RuntimeException {

    private long bookID;

    public BookNotFoundException(long id) {
        this.bookID = id;
    }

    public String getMessage() {
        return "Could not find book id: " + this.bookID;
    }
}
