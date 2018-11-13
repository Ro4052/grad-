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

    LibraryUser user1 = new LibraryUser("TestUser");

    @Test
    public void add_user() {
        service.add(user1);
        List<LibraryUser> users = service.findAll();
        assertArrayEquals(new LibraryUser[]{user1}, users.toArray());
    }

    @Test
    public void find_One_User() {
        service.add(user1);
        LibraryUser user = service.findOne("TestUser");
        assertEquals(user1, user);
    }

    @Test (expected = UserNotFoundException.class)
    public void user_Not_Found_Correct_Exception() {
        service.findOne("NoUser");
    }
}
