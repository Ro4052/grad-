package com.scottlogic.librarygradproject.Entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class LibraryUser {
    @Id
    private String username;

    public LibraryUser() { }

    public LibraryUser(String username) {
        this.username = username;
    }
}
