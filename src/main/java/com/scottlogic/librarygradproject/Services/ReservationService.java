package com.scottlogic.librarygradproject.Services;

import com.scottlogic.librarygradproject.Entities.LibraryUser;
import com.scottlogic.librarygradproject.Entities.Reservation;
import com.scottlogic.librarygradproject.Exceptions.UserNotFoundException;
import com.scottlogic.librarygradproject.Repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ReservationService {

    private final ReservationRepository resRepo;
    private final BookService bookService;
    private final UserService userService;

    @Autowired
    public ReservationService(ReservationRepository resRepo, BookService bookService, UserService userService) {
        this.resRepo = resRepo;
        this.bookService = bookService;
        this.userService = userService;
    }

    private void validateReservation(long bookId, String username) {
        bookService.findOne(bookId);
        userService.findOne(username);
    }

    public void reserve(long bookId) {
        String username = "Boss"; //sessions[token].username;
        validateReservation(bookId, username);
        Reservation reservation = Reservation.builder().bookId(bookId).username(username).build();
        resRepo.save(reservation);
    }

    public List<Reservation> findAll() {
        return resRepo.findAll();
    }

}
