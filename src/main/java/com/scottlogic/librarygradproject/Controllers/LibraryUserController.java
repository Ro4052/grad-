package com.scottlogic.librarygradproject.Controllers;

import com.scottlogic.librarygradproject.Entities.LibraryUser;
import com.scottlogic.librarygradproject.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LibraryUserController {

    private final UserService userService;

    @Autowired
    public LibraryUserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Map<String, Object> user(OAuth2Authentication authentication) {
        return userService.userInfo(authentication);
    }
}
