package com.scottlogic.librarygradproject;

import com.scottlogic.librarygradproject.Entities.Book;
import com.scottlogic.librarygradproject.Entities.LibraryUser;
import com.scottlogic.librarygradproject.Entities.Reservation;
import com.scottlogic.librarygradproject.Exceptions.BookNotFoundException;
import com.scottlogic.librarygradproject.Exceptions.ReservationNotFoundException;
import com.scottlogic.librarygradproject.Exceptions.UserNotFoundException;
import com.scottlogic.librarygradproject.Services.BookService;
import com.scottlogic.librarygradproject.Services.ReservationService;
import com.scottlogic.librarygradproject.Services.UserService;
import netscape.security.ForbiddenTargetException;
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

    private Book book1, book2;
    private Reservation res1, res2;
    private LibraryUser correctUser;
    private LibraryUser invalidUser;
    private OAuth2Authentication authentication;
    private OAuthClientTestHelper helper = new OAuthClientTestHelper("TestUser 1", "testuser 1", "avatar_url");

    @Before
    public void before_Each_Test() {
        authentication = helper.getOauthTestAuthentication();
        book1 = new Book("0123456789111", "Correct Book1", "Correct Author1", "2001");
        book2 = new Book("0123456789", "Correct Book2", "Correct Author2", "2002");
        res1 = new Reservation(1L, "TestUser 1", 1L);
        res1.setId(1);
        res2 = new Reservation(2L, "TestUser 1", 1L);
        res2.setId(2);
        correctUser = LibraryUser.builder()
                .userId("Boss")
                .name("boss")
                .avatarUrl("")
                .build();
        invalidUser = LibraryUser.builder()
                .userId("Not Boss")
                .name("not boss")
                .avatarUrl("")
                .build();

        bookService.save(book1);
        bookService.save(book2);
    }

    @Test(expected = BookNotFoundException.class)
    public void invalid_bookId_throws_exception() {
        //Arrange
        long id = 10;
        userService.add(correctUser);

        //Act
        reservationService.reserve(id, authentication);
    }

    @Test
    public void valid_book_and_user_creates_reservation() {

        //Arrange
        long id = 1;
        userService.add(correctUser);

        //Act
        reservationService.reserve(id, authentication);

        //Assert
        List<Reservation> reservations = reservationService.findAll();
        assertArrayEquals(new Reservation[] {res1}, reservations.toArray());
    }

    @Test
    public void get_with_valid_id_gets_reservation() {

        //Arrange
        userService.add(correctUser);
        reservationService.reserve(res1.getBookId(), authentication);
        reservationService.reserve(res2.getBookId(), authentication);

        //Act
        Reservation reservation = reservationService.findOne(res1.getId());

        //Assert
        assertEquals(reservation, res1);
    }

    @Test (expected = ReservationNotFoundException.class)
    public void get_with_invalid_id_throws_exception() {
        //Arrange
        userService.add(correctUser);
        reservationService.reserve(res1.getBookId(), authentication);

        //Act
        reservationService.findOne(10);
    }

    @Test
    public void getAll_returns_all_reservations() {
        //Arrange
        userService.add(correctUser);
        reservationService.reserve(res1.getBookId(), authentication);
        reservationService.reserve(res2.getBookId(), authentication);

        //Act
        List<Reservation> reservations = reservationService.findAll();

        //Assert
        assertArrayEquals(new Reservation[]{res1 , res2}, reservations.toArray());
    }

    @Test
    public void check_returns_correct_number_single_reservation() {
        //Arrange
        userService.add(correctUser);
        reservationService.reserve(res1.getBookId(), authentication);

        //Act
        long reservationNumber = reservationService.checkReservation(res1.getBookId());

        //Assert
        assertEquals(1L, reservationNumber);
    }

    @Test
    public void check_returns_correct_number_multiple_reservation() {
        //Arrange
        userService.add(correctUser);
        reservationService.reserve(res1.getBookId(), authentication);
        reservationService.reserve(res1.getBookId(), authentication);

        //Act
        long reservationNumber = reservationService.checkReservation(res1.getBookId());

        //Assert
        assertEquals(2L, reservationNumber);
    }

    @Test
    public void check_returns_correct_number_no_reservation() {
        //Act
        long reservationNumber = reservationService.checkReservation(res1.getBookId());

        //Assert
        assertEquals(0L, reservationNumber);
    }

}
