package com.scottlogic.librarygradproject;

import com.scottlogic.librarygradproject.Entities.Book;
import com.scottlogic.librarygradproject.Entities.Borrow;
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

    private OAuth2Authentication authentication;
    private OAuthClientTestHelper helper = new OAuthClientTestHelper("TestUser 1", "testuser 1", "avatar_url");
    private Book book1, book2, book3;
    private Borrow borrow1, borrow2, borrow3, borrow4;

    @Before
    public void before_Each_Test() {
        authentication = helper.getOauthTestAuthentication();
        book1 = new Book("0123456789111", "Correct Book1", "Correct Author1", "2001");
        bookService.save(book1);
        book2 = new Book("0123456789", "Correct Book2", "Correct Author2", "2018");
        bookService.save(book2);
        book3 = new Book("0123456789", "Correct Book2", "Correct Author2", "2018");
        bookService.save(book3);
        borrow1 = new Borrow(1, "TestUser 1", LocalDate.now(), true, LocalDate.now().plusDays(7));
        borrow1.setId(1);
        borrow2 = new Borrow(2, "TestUser 1", LocalDate.now(), true, LocalDate.now().plusDays(7));
        borrow2.setId(2);
        borrow3 = new Borrow(3, "TestUser 1", LocalDate.now().minusDays(8), true, LocalDate.now().minusDays(1));
        borrow3.setId(3);
        borrow4 = new Borrow(4, "TestUser 1", LocalDate.now().minusDays(8), false, LocalDate.now().minusDays(1));
        borrow4.setId(4);
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

    @Test
    public void delete_with_valid_borrowId_deletes_loan() {
        //Arrange
        long borrowToDeleteId = 1;
        borrowService.borrow(book1.getId(), authentication);
        borrowService.borrow(book2.getId(), authentication);

        //Act
        borrowService.delete(borrowToDeleteId);
        List<Borrow> loans = borrowService.findAll();

        //Assert
        assertArrayEquals(new Borrow[] {borrow2}, loans.toArray());
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
//  inactivate, delete first reservation and create new borrow and amend queue position
    @Test
    public void bookReturned_Multiple_Reservations_Pending() {
        //Arrange
        reservationService.reserve(book1.getId(), authentication);
        reservationService.reserve(book1.getId(), authentication);
        reservationService.reserve(book1.getId(), authentication);
        borrowService.borrow(book1.getId(), authentication);

        //Act
        borrowService.bookReturned(1L);
        borrow1.setActive(false);
        borrow2.setBookId(1);

        //Assert
        assertFalse(borrowService.findOne(1L).isActive());
        assertEquals(2, reservationService.findAll().size());
        assertEquals(reservationService.findOne(2L).getQueuePosition(), 1);
        assertEquals(reservationService.findOne(3L).getQueuePosition(), 2);
        assertArrayEquals(new Borrow[] {borrow1, borrow2}, borrowService.findAll().toArray());
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
        reservationService.reserve(book1.getId(), authentication);
        borrowService.borrow(book1.getId(), authentication);
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
        reservationService.reserve(book2.getId(), authentication);
        borrowService.borrow(book1.getId(), authentication);

        //Act
        borrowService.bookReturned(1L);

        //Assert
        assertEquals(1, reservationService.findAll().size());
        assertFalse(borrowService.findOne(1L).isActive());
    }

    @Test
    public void update_Borrowed_Updates_Repo() {
        //Arrange
        borrowService.borrow(borrow1.getBookId(), authentication);
        borrowService.borrow(borrow2.getBookId(), authentication);
        borrowService.borrow(borrow3.getBookId(), authentication);
        borrowService.borrow(borrow4.getBookId(), authentication);
        borrowService.findOne(3).setBorrowDate(borrow3.getBorrowDate());
        borrowService.findOne(3).setReturnDate(borrow3.getReturnDate());
        borrowService.findOne(4).setBorrowDate(borrow4.getBorrowDate());
        borrowService.findOne(4).setReturnDate(borrow4.getReturnDate());
        borrowService.findOne(4).setActive(false);
        borrow3.setActive(false);

        //Act
        borrowService.updateBorrowed(LocalDate.now());

        //Assert
        assertArrayEquals(new Borrow[] {borrow1, borrow2, borrow3, borrow4}, borrowService.findAll().toArray());
    }

    @Test
    public void update_Borrowed_With_Reservation_Creates_New_Borrow() {
        //Arrange
        borrowService.borrow(borrow1.getBookId(), authentication);
        borrowService.borrow(borrow2.getBookId(), authentication);
        borrowService.borrow(borrow3.getBookId(), authentication);
        borrowService.borrow(borrow4.getBookId(), authentication);
        borrowService.findOne(3).setBorrowDate(borrow3.getBorrowDate());
        borrowService.findOne(3).setReturnDate(borrow3.getReturnDate());
        borrowService.findOne(4).setBorrowDate(borrow4.getBorrowDate());
        borrowService.findOne(4).setReturnDate(borrow4.getReturnDate());
        borrowService.findOne(4).setActive(false);
        Borrow borrow5 = new Borrow(3, "TestUser 1", LocalDate.now(), true, LocalDate.now().plusDays(7));
        borrow5.setId(5L);
        borrow3.setId(3L);
        borrow3.setActive(false);
        reservationService.reserve(3L, authentication);

        //Act
        borrowService.updateBorrowed(LocalDate.now());

        //Assert
        assertArrayEquals(new Borrow[] {borrow1, borrow2, borrow3, borrow4, borrow5}, borrowService.findAll().toArray());
    }
}
