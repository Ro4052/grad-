package com.scottlogic.librarygradproject;

import com.scottlogic.librarygradproject.Entities.Book;
import com.scottlogic.librarygradproject.Entities.Borrow;
import com.scottlogic.librarygradproject.Entities.LibraryUser;
import com.scottlogic.librarygradproject.Entities.Reservation;
import com.scottlogic.librarygradproject.Exceptions.*;
import com.scottlogic.librarygradproject.Services.BookService;
import com.scottlogic.librarygradproject.Services.BorrowService;
import com.scottlogic.librarygradproject.Services.ReservationService;
import com.scottlogic.librarygradproject.Services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DataJpaTest
public class BorrowServiceTest {

    @Autowired
    private BorrowService borrowService;
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    @Autowired
    private ReservationService reservationService;

    LibraryUser user1 = LibraryUser.builder()
            .userId("TestUser 1")
            .name("testuser 1")
            .avatarUrl("avatar_url")
            .build();
    LibraryUser user2 = LibraryUser.builder()
            .userId("TestUser 2")
            .name("testuser 2")
            .avatarUrl("avatar_url")
            .build();
    LibraryUser user3 = LibraryUser.builder()
            .userId("TestUser 3")
            .name("testuser 3")
            .avatarUrl("avatar_url")
            .build();
    LibraryUser user4 = LibraryUser.builder()
            .userId("TestUser 4")
            .name("testuser 4")
            .avatarUrl("avatar_url")
            .build();

    private OAuth2Authentication authentication;
    private OAuthClientTestHelper helper = new OAuthClientTestHelper("TestUser 1", "testuser 1", "avatar_url");
    private OAuth2Authentication authentication2;
    private OAuthClientTestHelper helper2 = new OAuthClientTestHelper("TestUser 2", "testuser 2", "avatar_url");
    private OAuth2Authentication authentication3;
    private OAuthClientTestHelper helper3 = new OAuthClientTestHelper("TestUser 3", "testuser 3", "avatar_url");
    private OAuth2Authentication authentication4;
    private OAuthClientTestHelper helper4 = new OAuthClientTestHelper("TestUser 4", "testuser 4", "avatar_url");
    private Book book1, book2, book3, book4;
    private Borrow borrow1, borrow2, borrow3, borrow4;

    @Before
    public void before_Each_Test() {
        authentication = helper.getOauthTestAuthentication();
        authentication2 = helper2.getOauthTestAuthentication();
        authentication3 = helper3.getOauthTestAuthentication();
        authentication4 = helper4.getOauthTestAuthentication();
        book1 = new Book("0123456789111", "Correct Book1", "Correct Author1", "2001");
        bookService.add(book1);
        book2 = new Book("0123456789", "Correct Book2", "Correct Author2", "2018");
        bookService.add(book2);
        book3 = new Book("0123456789", "Correct Book3", "Correct Author3", "2016");
        bookService.add(book3);
        book4 = new Book("1111111111", "Correct Book4", "Correct Author4", "1999");
        bookService.add(book4);
        borrow1 = new Borrow(1, "TestUser 1", LocalDate.now(), true, LocalDate.now().plusDays(7));
        borrow1.setId(1);
        borrow2 = new Borrow(2, "TestUser 1", LocalDate.now(), true, LocalDate.now().plusDays(7));
        borrow2.setId(2);
        borrow3 = new Borrow(3, "TestUser 1", LocalDate.now().minusDays(8), true, LocalDate.now().minusDays(1));
        borrow3.setId(3);
        borrow4 = new Borrow(4, "TestUser 1", LocalDate.now().minusDays(8), false, LocalDate.now().minusDays(1));
        borrow4.setId(4);
        userService.add(user1);
        userService.add(user2);
        userService.add(user3);
        userService.add(user4);
    }

    @Test(expected = BookNotFoundException.class)
    public void invalid_bookId_throws_exception() {
        //Arrange
        long bookId = 10;

        //Act
        borrowService.borrow(bookId, authentication);
    }

    @Test
    public void valid_book_and_user_creates_loan() {
        //Arrange
        long bookId = 1;

        //Act
        borrowService.borrow(bookId, authentication);

        //Assert
        List<Borrow> loans = borrowService.findAll();
        assertArrayEquals(new Borrow[] {borrow1}, loans.toArray());
    }

    @Test
    public void get_with_valid_id_gets_reservation() {

        //Arrange
        borrowService.borrow(borrow1.getBookId(), authentication);

        //Act
        Borrow borrow = borrowService.findOne(borrow1.getId());

        //Assert
        assertEquals(borrow, borrow1);
    }

    @Test (expected = BorrowNotFoundException.class)
    public void get_with_invalid_id_throws_exception() {
        //Arrange
        long invalidBorrowId = 10;
        borrowService.borrow(book1.getId(), authentication);

        //Act
        borrowService.findOne(invalidBorrowId);
    }

    @Test
    public void getAll_returns_all_loans() {
        //Arrange
        borrowService.borrow(book1.getId(), authentication);
        borrowService.borrow(book2.getId(), authentication);

        //Act
        List<Borrow> loans = borrowService.findAll();

        //Assert
        assertArrayEquals(new Borrow[]{borrow1, borrow2}, loans.toArray());
    }

    @Test
    public void check_returns_true_when_book_has_active_loans() {
        //Arrange
        long bookId = 1;
        borrowService.borrow(bookId, authentication);

        //Assert
        assertTrue(borrowService.isBorrowed(bookId));
    }

    @Test
    public void check_returns_false_when_book_has_no_active_loans() {
        //Assert
        long bookId = 1;
        assertFalse(borrowService.isBorrowed(bookId));
    }

    @Test (expected = BorrowNotFoundException.class)
    public void delete_with_invalid_borrowId_throws_exception() {
        //Arrange
        long invalidBorrowId = 10;
        borrowService.borrow(book1.getId(), authentication);

        //Act
        borrowService.delete(invalidBorrowId);
    }

    //  Three reservations for a book and an active borrow ->
//  inactivate, set collection date for first reservation in queue
    @Test
    public void bookReturned_Multiple_Reservations_Pending() {
        //Arrange
        borrowService.borrow(book1.getId(), authentication);
        reservationService.reserve(book1.getId(), authentication2);
        reservationService.reserve(book1.getId(), authentication3);
        reservationService.reserve(book1.getId(), authentication4);

        //Act
        borrowService.bookReturned(1L);
        borrow1.setActive(false);
        borrow1.setReturnDate(LocalDate.now());

        //Assert
        assertFalse(borrowService.findOne(1L).isActive());
        assertEquals(3, reservationService.findAll().size());
        assertEquals(reservationService.findOne(1L).getCollectBy(), LocalDate.now().plusDays(3));
        assertArrayEquals(new Borrow[] {borrow1}, borrowService.findAll().toArray());
    }

    //    no active borrows ->
//    throw an exception
    @Test (expected = BookAlreadyReturnedException.class)
    public void bookReturned_No_Active_Borrows_Throws() {
        //Arrange
        borrowService.borrow(book1.getId(), authentication);
        borrowService.findOne(1).setActive(false);

        //Act
        borrowService.bookReturned(1);
    }

    @Test (expected = BorrowNotFoundException.class)
    public void bookReturned_No_Borrows_Throws() {
        borrowService.bookReturned(1);
    }

    //  can't create a new borrow if one already exists after a book is returned
    @Test (expected = BookAlreadyBorrowedException.class)
    public void bookReturned_Two_Active_Borrows_Throws() {
        //Arrange
        borrowService.borrow(book1.getId(), authentication);
        reservationService.reserve(book1.getId(), authentication);
        borrowService.borrow(book2.getId(), authentication);
        borrowService.findOne(2L).setBookId(1L);

        //Act
        borrowService.bookReturned(1L);

    }

    //  no reservation for the book and an active borrow ->
//  inactivate borrow, do nothing to reservations table
    @Test
    public void bookReturned_No_Reservations_Pending() {
        //Arrange
        borrowService.borrow(book1.getId(), authentication);

        //Act
        borrowService.bookReturned(1L);

        //Assert
        assertFalse(borrowService.findOne(1L).isActive());
    }

    @Test
    public void update_Borrowed_Updates_Repo() {
        //Arrange - borrow books 1-3
        borrowService.borrow(borrow1.getBookId(), authentication);
        borrowService.borrow(borrow2.getBookId(), authentication);
        borrowService.borrow(borrow3.getBookId(), authentication);

        //Arrange - match db borrow dates to test dates
        borrowService.findOne(3).setBorrowDate(borrow3.getBorrowDate());
        borrowService.findOne(3).setReturnDate(borrow3.getReturnDate());

        //Arrange - expect db changes to match:
        borrow3.setActive(false);
        borrow3.setReturnDate(LocalDate.now());

        //Act
        borrowService.updateBorrowed(LocalDate.now());

        //Assert
        assertArrayEquals(new Borrow[] {borrow1, borrow2, borrow3}, borrowService.findAll().toArray());
    }
}