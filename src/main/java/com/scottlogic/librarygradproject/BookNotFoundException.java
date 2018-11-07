package com.scottlogic.librarygradproject;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookNotFoundException extends RuntimeException {

    private List<Integer> bookIDs = new ArrayList<>();

    public BookNotFoundException(int id) {
        this.bookIDs.add(id);
    }
    public BookNotFoundException(List<Integer> listOfIds) {
        this.bookIDs = listOfIds;
    }

    public String getMessage() {
        final StringBuilder returnMessage = new StringBuilder("Could not find book ids:");
        bookIDs.forEach(id -> returnMessage.append(" " + id));
        return returnMessage.toString();
    }
}
