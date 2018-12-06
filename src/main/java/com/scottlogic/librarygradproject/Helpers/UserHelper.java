package com.scottlogic.librarygradproject.Helpers;

import com.scottlogic.librarygradproject.Entities.LibraryUser;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.Map;

public class UserHelper {

    @SuppressWarnings("unchecked")
    public LibraryUser getUserDetails(OAuth2Authentication authentication) {
        Map<String, String> userDetails = (Map<String, String>) authentication.getUserAuthentication().getDetails();
        LibraryUser newUser = LibraryUser.builder()
                .userId(userDetails.get("login"))
                .name(userDetails.get("name"))
                .avatarUrl(userDetails.get("avatar_url"))
                .build();
        return newUser;
    }
}
