package com.scottlogic.librarygradproject;

import com.scottlogic.librarygradproject.Controllers.BooksController;
import com.scottlogic.librarygradproject.Controllers.LibraryUserController;
import com.scottlogic.librarygradproject.Entities.LibraryUser;
import com.scottlogic.librarygradproject.Services.BookService;
import com.scottlogic.librarygradproject.Services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import static org.mockito.Mockito.*;

public class UserControllerTest {

    UserService service;
    LibraryUserController controller;

    @Before
    public void before_Each_Test() {
        service = mock(UserService.class);
        controller = new LibraryUserController(service);
    }

    @Test
    public void loggedIn_Calls_Service_LoggedIn() {

        OAuthClientTestHelper helper = new OAuthClientTestHelper("TestUser 1", "testuser 1", "avatar_url");
        OAuth2Authentication authentication = helper.getOauthTestAuthentication();
        controller.user(authentication);
        verify(service).loggedIn(authentication);
    }


}
