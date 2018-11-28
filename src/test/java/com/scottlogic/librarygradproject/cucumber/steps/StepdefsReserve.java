package com.scottlogic.librarygradproject.cucumber.steps;

import com.scottlogic.librarygradproject.Controllers.BorrowController;
import com.scottlogic.librarygradproject.Entities.LibraryUser;
import com.scottlogic.librarygradproject.Exceptions.BookAlreadyBorrowedException;
import com.scottlogic.librarygradproject.OAuthClientTestHelper;
import com.scottlogic.librarygradproject.Services.BorrowService;
import cucumber.api.PendingException;
import cucumber.api.java8.En;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StepdefsReserve implements En {
    private boolean bookAlreadyBorrowedExceptionThrown = false;

    @Autowired
    BorrowService borrowService;
    BorrowController borrowController;
    long id;
    LibraryUser user1 = LibraryUser.builder()
            .userId("Boss")
            .name("Boss")
            .avatarUrl("avatar_url")
            .build();
    OAuthClientTestHelper helper = new OAuthClientTestHelper("Boss", "Boss", "avatar_url");
    OAuth2Authentication authentication = helper.getOauthTestAuthentication();

    public StepdefsReserve() {

        And("^an empty borrow table exists$", () -> {
            borrowService.deleteAll();
            borrowController = new BorrowController(borrowService);
        });

        When("^a borrow request is made on an available book by an authorised user$", () -> {
            borrowService.borrow(id, authentication);
        });


        Then("^that borrow is added to the database$", () -> {
            assertEquals(borrowService.isBorrowed(id),1);
        });

        And("^then another borrow request is made for the same book by an authorised user$", () -> {
            try {
                borrowService.borrow(id, authentication);
            } catch (BookAlreadyBorrowedException e) {
                bookAlreadyBorrowedExceptionThrown = true;
            }
        });

        Then("^an exception is thrown$", () -> {
            assertTrue(bookAlreadyBorrowedExceptionThrown);
            bookAlreadyBorrowedExceptionThrown = false;
        });

        Then("^the return date is set to seven days from the current day$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });

        Then("^the book is auto returned at midnight after a week$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });

        Then("^when the book is auto returned the reservation moves to borrowed$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });

        When("^a book is being borrowed by a user$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });





    }


}
