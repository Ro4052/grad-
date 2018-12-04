package com.scottlogic.librarygradproject;

import com.scottlogic.librarygradproject.Entities.Book;
import com.scottlogic.librarygradproject.Entities.LibraryUser;
import com.scottlogic.librarygradproject.Entities.Reservation;
import com.scottlogic.librarygradproject.Exceptions.AlreadyReservedException;
import com.scottlogic.librarygradproject.Exceptions.BookAlreadyBorrowedException;
import com.scottlogic.librarygradproject.Exceptions.BookNotFoundException;
import com.scottlogic.librarygradproject.Exceptions.ReservationNotFoundException;
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
import static org.junit.Assert.*;

import java.util.List;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DataJpaTest
public class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    @Autowired
    private BorrowService borrowService;

    private Book book1, book2;
    private Reservation res1, res2;
    private OAuthClientTestHelper helper = new OAuthClientTestHelper("TestUser 1", "testuser 1", "avatar_url");
    private OAuth2Authentication authentication;
    private OAuthClientTestHelper helper2 = new OAuthClientTestHelper("TestUser 2", "testuser 2", "avatar_url");
    private OAuth2Authentication authentication2;
    private OAuthClientTestHelper helper3 = new OAuthClientTestHelper("TestUser 3", "testuser 3", "avatar_url");
    private OAuth2Authentication authentication3;
    private OAuthClientTestHelper helper4 = new OAuthClientTestHelper("TestUser 4", "testuser 4", "avatar_url");
    private OAuth2Authentication authentication4;
    private OAuthClientTestHelper helper5 = new OAuthClientTestHelper("TestUser 5", "testuser 5", "avatar_url");
    private OAuth2Authentication authentication5;
    private OAuthClientTestHelper helper6 = new OAuthClientTestHelper("TestUser 6", "testuser 6", "avatar_url");
    private OAuth2Authentication authentication6;

    LibraryUser user1 = LibraryUser.builder().userId("TestUser 1").name("testuser 1").avatarUrl("avatar_url").build();
    LibraryUser user2 = LibraryUser.builder().userId("TestUser 2").name("testuser 2").avatarUrl("").build();
    LibraryUser user3 = LibraryUser.builder().userId("TestUser 3").name("testuser 3").avatarUrl("").build();
    LibraryUser user4 = LibraryUser.builder().userId("TestUser 4").name("testuser 4").avatarUrl("").build();
    LibraryUser user5 = LibraryUser.builder().userId("TestUser 5").name("testuser 5").avatarUrl("").build();
    LibraryUser user6 = LibraryUser.builder().userId("TestUser 6").name("testuser 6").avatarUrl("").build();

    @Before
    public void before_Each_Test() {
        authentication = helper.getOauthTestAuthentication();
        authentication2 = helper2.getOauthTestAuthentication();
        authentication3 = helper3.getOauthTestAuthentication();
        authentication4 = helper4.getOauthTestAuthentication();
        authentication5 = helper5.getOauthTestAuthentication();
        authentication6 = helper6.getOauthTestAuthentication();
        book1 = new Book("0123456789111", "Correct Book1", "Correct Author1", "2001");
        book2 = new Book("0123456789", "Correct Book2", "Correct Author2", "2002");
        res1 = new Reservation(1L, "TestUser 1", 1L, null);
        res1.setId(1);
        res2 = new Reservation(2L, "TestUser 1", 1L, null);
        res2.setId(2);

        bookService.save(book1);
        bookService.save(book2);

        userService.add(user1);
        userService.add(user2);
        userService.add(user3);
        userService.add(user4);
        userService.add(user5);
        userService.add(user6);
    }

    @Test(expected = BookNotFoundException.class)
    public void invalid_bookId_throws_exception() {
        // Arrange
        long invalidBookId = 10;

        // Act
        reservationService.reserve(invalidBookId, authentication);
    }

    @Test
    public void valid_book_and_user_creates_reservation() {
        // Arrange
        long bookId = 1;

        // Act
        borrowService.borrow(bookId, authentication2);
        reservationService.reserve(bookId, authentication);

        // Assert
        List<Reservation> reservations = reservationService.findAll();
        assertArrayEquals(new Reservation[] { res1 }, reservations.toArray());
    }

    @Test
    public void get_with_valid_id_gets_reservation() {
        // Arrange
        borrowService.borrow(res1.getBookId(), authentication2);
        borrowService.borrow(res2.getBookId(), authentication2);
        reservationService.reserve(res1.getBookId(), authentication);
        reservationService.reserve(res2.getBookId(), authentication);

        // Act
        Reservation reservation = reservationService.findOne(res1.getId());

        // Assert
        assertEquals(reservation, res1);
    }

    @Test(expected = ReservationNotFoundException.class)
    public void get_with_invalid_id_throws_exception() {
        // Arrange
        borrowService.borrow(res1.getBookId(), authentication);
        reservationService.reserve(res1.getBookId(), authentication2);
        long invalidReservationId = 10;

        // Act
        reservationService.findOne(invalidReservationId);
    }

    @Test
    public void getAll_returns_all_reservations() {
        // Arrange
        borrowService.borrow(res1.getBookId(), authentication2);
        borrowService.borrow(res2.getBookId(), authentication2);
        reservationService.reserve(res1.getBookId(), authentication);
        reservationService.reserve(res2.getBookId(), authentication);

        // Act
        List<Reservation> reservations = reservationService.findAll();

        // Assert
        assertArrayEquals(new Reservation[] { res1, res2 }, reservations.toArray());
    }

    @Test
    public void check_returns_correct_number_single_reservation() {
        // Arrange
        borrowService.borrow(res1.getBookId(), authentication);
        reservationService.reserve(res1.getBookId(), authentication2);

        // Act
        long reservationNumber = reservationService.checkReservation(res1.getBookId());

        // Assert
        assertEquals(1L, reservationNumber);
    }

    @Test
    public void check_returns_correct_number_multiple_reservation() {
        // Arrange
        borrowService.borrow(res1.getBookId(), authentication);
        reservationService.reserve(res1.getBookId(), authentication2);
        reservationService.reserve(res1.getBookId(), authentication3);

        // Act
        long reservationNumber = reservationService.checkReservation(res1.getBookId());

        // Assert
        assertEquals(2L, reservationNumber);
    }

    @Test
    public void check_returns_correct_number_no_reservation() {
        // Act
        long reservationNumber = reservationService.checkReservation(res1.getBookId());

        // Assert
        assertEquals(0L, reservationNumber);
    }

    @Test
    public void delete_reorders_queue() {
        // Arrange
        Reservation reservation1 = new Reservation(2, "TestUser 2", 1, null);
        reservation1.setId(1);
        Reservation reservation2 = new Reservation(2, "TestUser 3", 2, null);
        reservation2.setId(2);
        Reservation reservation3 = new Reservation(2, "TestUser 4", 3, null);
        reservation3.setId(3);
        Reservation reservation4 = new Reservation(2, "TestUser 5", 4, null);
        reservation4.setId(4);
        Reservation reservation5 = new Reservation(2, "TestUser 6", 5, null);
        reservation5.setId(5);

        borrowService.borrow(reservation1.getBookId(), authentication);
        reservationService.reserve(reservation1.getBookId(), authentication2);
        reservationService.reserve(reservation2.getBookId(), authentication3);
        reservationService.reserve(reservation3.getBookId(), authentication4);
        reservationService.reserve(reservation4.getBookId(), authentication5);
        reservationService.reserve(reservation5.getBookId(), authentication6);

        // Act
        reservationService.delete(reservation3.getId());
        List<Reservation> newReservations = reservationService.findAll();

        // Assert
        reservation4.setQueuePosition(3);
        reservation5.setQueuePosition(4);
        assertArrayEquals(new Reservation[] { reservation1, reservation2, reservation4, reservation5 },
                newReservations.toArray());
    }

    @Test(expected = AlreadyReservedException.class)
    public void reserve_throws_if_book_already_reserved_by_user() {
        // Arrange
        borrowService.borrow(res1.getBookId(), authentication2);
        reservationService.reserve(res1.getBookId(), authentication);

        // Act
        reservationService.reserve(res1.getBookId(), authentication);
    }

    @Test(expected = BookAlreadyBorrowedException.class)
    public void reserve_throws_if_book_already_borrowed_by_user() {
        // Arrange
        borrowService.borrow(res1.getBookId(), authentication);
        reservationService.reserve(res1.getBookId(), authentication);
    }

    @Test
    public void reserve_allowed_if_previous_borrow_is_inactive() {
        // Arrange
        borrowService.borrow(res1.getBookId(), authentication);
        borrowService.bookReturned(res1.getBookId());
        borrowService.borrow(res1.getBookId(), authentication2);
        reservationService.reserve(res1.getBookId(), authentication);
    }
}
