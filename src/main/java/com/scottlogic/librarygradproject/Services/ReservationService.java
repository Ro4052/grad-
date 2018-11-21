package com.scottlogic.librarygradproject.Services;

import com.scottlogic.librarygradproject.Entities.Reservation;
import com.scottlogic.librarygradproject.Exceptions.ReservationNotFoundException;
import com.scottlogic.librarygradproject.Repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

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

    private void validateReservation(long bookId, String userId) {
        bookService.findOne(bookId);
        userService.findOne(userId);
    }

    public long checkReservation(long bookId) {
        return resRepo.findLatestQueue(bookId);
    }

    public void reserve(long bookId, OAuth2Authentication authentication) {
        String userId = userService.loggedIn(authentication).getUserId();
        validateReservation(bookId, userId);
        long nextInQueue = resRepo.findLatestQueue(bookId) + 1;
//        if (nextInQueue == 1) {
//             check if book is borrowed - if it is not then borrow the book
//        }
//        else
        Reservation reservation = Reservation.builder().bookId(bookId).userId(userId).queuePosition(nextInQueue).build();
        resRepo.save(reservation);
    }

    public List<Reservation> findAll() {
        return resRepo.findAll();
    }

    public void delete(long reservationId) {
        try {
            resRepo.deleteById(reservationId);
        } catch (EmptyResultDataAccessException e) {
            throw new ReservationNotFoundException(reservationId);
        }
    }

    public void deleteAll() {
        this.resRepo.deleteAll();
    }

    public Reservation findOne(long reservationId) {
        Optional<Reservation> reservationToGet = resRepo.findById(reservationId);
        return reservationToGet.orElseThrow(() -> new ReservationNotFoundException(reservationId));
    }
}
