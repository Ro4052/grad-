package com.scottlogic.librarygradproject;

import com.scottlogic.librarygradproject.Entities.LibraryUser;
import com.scottlogic.librarygradproject.Exceptions.UserNotFoundException;
import com.scottlogic.librarygradproject.Services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;
import java.security.Principal;
import java.util.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DataJpaTest
public class UserServiceTest {

    @Autowired
    UserService service;

    LibraryUser user1 = LibraryUser.builder()
            .userId("TestUser 1")
            .name("testuser 1")
            .avatarUrl("avatar_url")
            .build();
    LibraryUser user2 = LibraryUser.builder()
            .userId("TestUser 2")
            .name("testuser 2")
            .avatarUrl("")
            .build();
    LibraryUser user3 = LibraryUser.builder()
            .userId("TestUser 3")
            .name("testuser 3")
            .avatarUrl("")
            .build();

    @Test
    public void add_user() {
        service.add(user1);
        List<LibraryUser> users = service.findAll();
        assertArrayEquals(new LibraryUser[]{user1}, users.toArray());
    }

    @Test
    public void find_One_User() {
        service.add(user1);
        service.add(user2);
        service.add(user3);
        LibraryUser user = service.findOne("TestUser 1");
        assertEquals(user1, user);
    }

    @Test
    public void loggedIn_User_Works() {
        OAuthClientTestHelper helper = new OAuthClientTestHelper("TestUser 1", "testuser 1", "avatar_url");
        OAuth2Authentication authentication = helper.getOauthTestAuthentication();
        service.loggedIn(authentication);
        assertArrayEquals(new LibraryUser[] {user1}, service.findAll().toArray());

    }

    @Test (expected = UserNotFoundException.class)
    public void user_Not_Found_Correct_Exception() {
        service.findOne("NoUser");
    }
}
