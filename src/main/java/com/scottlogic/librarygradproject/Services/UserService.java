package com.scottlogic.librarygradproject.Services;

import com.scottlogic.librarygradproject.Entities.LibraryUser;
import com.scottlogic.librarygradproject.Exceptions.UserNotFoundException;
import com.scottlogic.librarygradproject.Repositories.LibraryUserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class UserService {

    private final LibraryUserRepository userRepo;

    @Autowired
    public UserService(LibraryUserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public List<LibraryUser> findAll() {
        return userRepo.findAll();
    }

    public LibraryUser findOne(String username) {
        Optional<LibraryUser> User = Optional.ofNullable(userRepo.findOne(username));
        return User.orElseThrow(() -> new UserNotFoundException(username));
    }

    public void add(LibraryUser user) {
        userRepo.save(user);
    }
}

