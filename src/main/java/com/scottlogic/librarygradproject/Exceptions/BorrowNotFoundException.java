package com.scottlogic.librarygradproject.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BorrowNotFoundException extends RuntimeException {

    public BorrowNotFoundException(Long id) {
        super("Cannot find the loan with id: " + id);
    }

}

