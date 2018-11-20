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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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

    @Before
    public void before_Each_Test() {
        book1 = new Book("0123456789111", "Correct Book1", "Correct Author1", "2001");
        book2 = new Book("0123456789", "Correct Book2", "Correct Author2", "2002");
        res1 = new Reservation(1L, "Boss", 1L);
        res1.setId(1);
        res2 = new Reservation(2L, "Boss", 1L);
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
        reservationService.reserve(id);
    }

    @Test(expected = UserNotFoundException.class)
    public void invalid_userId_throws_exception() {
        //Arrange
        long id = 1;
        userService.add(invalidUser);

        //Act
        reservationService.reserve(id);
    }

    @Test
    public void valid_book_and_user_creates_reservation() {
        //Arrange
        long id = 1;
        userService.add(correctUser);

        //Act
        reservationService.reserve(id);

        //Assert
        List<Reservation> reservations = reservationService.findAll();
        assertArrayEquals(new Reservation[] {res1}, reservations.toArray());
    }

    @Test
    public void get_with_valid_id_gets_reservation() {
        //Arrange
        userService.add(correctUser);
        reservationService.reserve(res1.getBookId());
        reservationService.reserve(res2.getBookId());

        //Act
        Reservation reservation = reservationService.findOne(res1.getId());

        //Assert
        assertEquals(reservation, res1);
    }

    @Test (expected = ReservationNotFoundException.class)
    public void get_with_invalid_id_throws_exception() {
        //Arrange
        userService.add(correctUser);
        reservationService.reserve(res1.getBookId());

        //Act
        reservationService.findOne(10);
    }

    @Test
    public void getAll_returns_all_reservations() {
        //Arrange
        userService.add(correctUser);
        reservationService.reserve(res1.getBookId());
        reservationService.reserve(res2.getBookId());

        //Act
        List<Reservation> reservations = reservationService.findAll();

        //Assert
        assertArrayEquals(new Reservation[]{res1 , res2}, reservations.toArray());
    }

    @Test
    public void check_returns_correct_number_single_reservation() {
        //Arrange
        userService.add(correctUser);
        reservationService.reserve(res1.getBookId());

        //Act
        long reservationNumber = reservationService.checkReservation(res1.getBookId());

        //Assert
        assertEquals(1L, reservationNumber);
    }

    @Test
    public void check_returns_correct_number_multiple_reservation() {
        //Arrange
        userService.add(correctUser);
        reservationService.reserve(res1.getBookId());
        reservationService.reserve(res1.getBookId());

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
