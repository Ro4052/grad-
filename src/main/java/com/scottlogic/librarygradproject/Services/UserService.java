package com.scottlogic.librarygradproject.Services;

import com.scottlogic.librarygradproject.Entities.LibraryUser;
import com.scottlogic.librarygradproject.Exceptions.UserNotFoundException;
import com.scottlogic.librarygradproject.Repositories.LibraryUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.List;
import java.util.Map;
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
        Optional<LibraryUser> User = userRepo.findById(username);
        return User.orElseThrow(() -> new UserNotFoundException(username));
    }

    public void add(LibraryUser user) {
        userRepo.save(user);
    }

    public LibraryUser loggedIn(OAuth2Authentication authentication) {
        Map<String, String> userDetails = (Map<String, String>) authentication.getUserAuthentication().getDetails();
        LibraryUser newUser = LibraryUser.builder()
                .userId(userDetails.get("login"))
                .name(userDetails.get("name"))
                .avatarUrl(userDetails.get("avatar_url"))
                .build();
        return userRepo.save(newUser);
    }
}

