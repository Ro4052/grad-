package com.scottlogic.librarygradproject;

import com.scottlogic.librarygradproject.Entities.LibraryUser;
import com.scottlogic.librarygradproject.Exceptions.UserNotFoundException;
import com.scottlogic.librarygradproject.Services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DataJpaTest
public class UserServiceTest {

    @Autowired
    UserService service;

    LibraryUser user1 = LibraryUser.builder()
            .username("TestUser 1")
            .name("testuser 1")
            .avatarUrl("")
            .build();
    LibraryUser user2 = LibraryUser.builder()
            .username("TestUser 2")
            .name("testuser 2")
            .avatarUrl("")
            .build();
    LibraryUser user3 = LibraryUser.builder()
            .username("TestUser 3")
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

    @Test (expected = UserNotFoundException.class)
    public void user_Not_Found_Correct_Exception() {
        service.findOne("NoUser");
    }
}
