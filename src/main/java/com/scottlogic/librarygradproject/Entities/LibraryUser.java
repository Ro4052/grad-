package com.scottlogic.librarygradproject.Entities;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@Builder
public class LibraryUser {
    @Id
    private String username;
    private String name;
    private String avatarUrl;

    public LibraryUser() {
    }

    public LibraryUser(String username, String name, String avatarUrl) {

        this.username = username;
        this.name = name;
        this.avatarUrl = avatarUrl;
    }
}
