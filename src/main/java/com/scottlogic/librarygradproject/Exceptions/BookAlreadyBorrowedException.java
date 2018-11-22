package com.scottlogic.librarygradproject.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class BookAlreadyBorrowedException extends RuntimeException{
    public BookAlreadyBorrowedException(Long id) {
        super("book with id " + id + " is already borrowed");
    }
}
