package com.scottlogic.librarygradproject;

import com.scottlogic.librarygradproject.Entities.Book;
import com.scottlogic.librarygradproject.Entities.LibraryUser;
import com.scottlogic.librarygradproject.Entities.Reservation;
import com.scottlogic.librarygradproject.Exceptions.BookNotFoundException;
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

    private Book book1;
    private Reservation res1;
    private LibraryUser correctUser;
    private LibraryUser invalidUser;

    @Before
    public void before_Each_Test() {
        book1 = new Book("0123456789111", "Correct Book1", "Correct Author1", "2001");
        res1 = new Reservation(1L, "Boss", 0L);
        res1.setId(1);
        correctUser = new LibraryUser("Boss");
        invalidUser = new LibraryUser("Not Boss");
    }

    @Test(expected = BookNotFoundException.class)
    public void invalid_bookId_throws_exception() {
        //Arrange
        long id = 10;
        bookService.save(book1);
        userService.add(correctUser);

        //Act
        reservationService.reserve(id);
    }

    @Test(expected = UserNotFoundException.class)
    public void invalid_userId_throws_exception() {
        //Arrange
        long id = 1;
        bookService.save(book1);
        userService.add(invalidUser);

        //Act
        reservationService.reserve(id);
    }

    @Test
    public void valid_book_and_user_creates_reservation() {
        //Arrange
        long id = 1;
        bookService.save(book1);
        System.out.println(res1);
        userService.add(correctUser);

        //Act
        reservationService.reserve(id);

        //Assert
        List<Reservation> reservations = reservationService.findAll();
        assertArrayEquals(new Reservation[] {res1}, reservations.toArray());
    }
}
