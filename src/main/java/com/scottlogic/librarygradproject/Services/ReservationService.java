package com.scottlogic.librarygradproject.Services;

import com.scottlogic.librarygradproject.Entities.Reservation;
import com.scottlogic.librarygradproject.Exceptions.ReservationNotFoundException;
import com.scottlogic.librarygradproject.Repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;

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

    public long checkReservation(long bookId) {
        return resRepo.findLatestQueue(bookId);
    }

    public void reserve(long bookId) {
        String username = "Boss"; //sessions[token].username;
        validateReservation(bookId, username);
        long nextInQueue = resRepo.findLatestQueue(bookId) + 1;
//        if (nextInQueue == 1) {
//             check if book is borrowed - if it is not then borrow the book
//        }
//        else
        Reservation reservation = Reservation.builder().bookId(bookId).username(username).queuePosition(nextInQueue).build();
        resRepo.save(reservation);
    }

    public List<Reservation> findAll() {
        return resRepo.findAll();
    }

    public void delete(long reservationId) {
        try {
            resRepo.delete(reservationId);
        } catch (EmptyResultDataAccessException e) {
            throw new ReservationNotFoundException(reservationId);
        }
    }

    public Reservation findOne(long reservationId) {
        Optional<Reservation> reservationToGet = Optional.ofNullable(resRepo.findOne(reservationId));
        return reservationToGet.orElseThrow(() -> new ReservationNotFoundException(reservationId));
    }
}
