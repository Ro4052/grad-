package com.scottlogic.librarygradproject;

import com.scottlogic.librarygradproject.Controllers.BorrowController;
import com.scottlogic.librarygradproject.Services.BorrowService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;

public class BorrowControllerTest {

    BorrowService borrowService;
    BorrowController borrowController;
    OAuthClientTestHelper helper = new OAuthClientTestHelper("TestUser 1", "testuser 1", "avatar_url");
    OAuth2Authentication authentication;

    @Before
    public void before_each_test() {
        authentication = helper.getOauthTestAuthentication();
        borrowService = mock(BorrowService.class);
        borrowController = new BorrowController(borrowService);
    }

    @Test
    public void borrow_calls_repo_borrow() {
        //Arrange
            long bookId = 1;

        //Act
            borrowController.post(bookId, authentication);

        //Assert
            verify(borrowService).borrow(bookId, authentication);
    }

    @Test
    public void delete_calls_repo_delete() {
        //Arrange
        long borrowId = 1;

        //Act
        borrowController.delete(borrowId);

        //Assert
        verify(borrowService).delete(borrowId);
    }

    @Test
    public void get_calls_repo_get() {
        //Arrange
        long borrowId = 1;

        //Act
        borrowController.get(borrowId);

        //Assert
        verify(borrowService).findOne(borrowId);
    }

    @Test
    public void getAll_calls_repo_findAll() {
        //Act
        borrowController.getAll();

        //Assert
        verify(borrowService).findAll();
    }

    @Test
    public void check_calls_repo_check() {
        //Arrange
        long bookId = 1;

        //Act
        borrowController.check(bookId);

        //Assert
        verify(borrowService).isBorrowed(bookId);
    }
}
