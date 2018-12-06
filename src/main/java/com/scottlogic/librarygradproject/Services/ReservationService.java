package com.scottlogic.librarygradproject.Services;

import com.scottlogic.librarygradproject.Entities.Reservation;
import com.scottlogic.librarygradproject.Exceptions.*;
import com.scottlogic.librarygradproject.Helpers.BorrowHelper;
import com.scottlogic.librarygradproject.Repositories.BookRepository;
import com.scottlogic.librarygradproject.Repositories.BorrowRepository;
import com.scottlogic.librarygradproject.Repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReservationService {

    private final ReservationRepository resRepo;
    private final BookRepository bookRepo;
    private final UserService userService;
    private final BorrowHelper borrowHelper;
    private final BorrowRepository borrowRepo;

    @Autowired
    public ReservationService(ReservationRepository resRepo, BookRepository bookRepo, UserService userService,
                              BorrowHelper borrowHelper, BorrowRepository borrowRepo) {
        this.resRepo = resRepo;
        this.bookRepo = bookRepo;
        this.userService = userService;
        this.borrowHelper = borrowHelper;
        this.borrowRepo = borrowRepo;
    }

    private void validateReservation(long bookId, String userId) {
        if (!bookRepo.existsById(bookId)) {
            throw new BookNotFoundException(bookId);
        }
        userService.findOne(userId);
    }

    public long checkReservation(long bookId) {
        return resRepo.findLatestQueue(bookId);
    }

    public Reservation reserve(long bookId, OAuth2Authentication authentication) {
        String userId = userService.getUserDetails(authentication).getUserId();
        if (resRepo.existsByUserIdAndBookId(userId, bookId)) {
            throw new AlreadyReservedException(bookId);
        }
        if (borrowRepo.existsByUserIdAndBookIdAndIsActive(userId, bookId, true)) {
            throw new BookAlreadyBorrowedException(bookId);
        }
        if (!borrowHelper.isBorrowed(bookId)) {
            throw new BookIsAvailableException(bookId);
        }
        validateReservation(bookId, userId);
        long nextInQueue = resRepo.findLatestQueue(bookId) + 1;
        Reservation reservation = Reservation.builder().bookId(bookId).userId(userId).queuePosition(nextInQueue).build();
        return resRepo.save(reservation);
    }

    public List<Reservation> findAll() {
        return resRepo.findAll();
    }

    public void delete(long reservationId) {
        try {
            Reservation resToDelete = resRepo.getOne(reservationId);
            AtomicLong queuePosition = new AtomicLong(resToDelete.getQueuePosition());
            resRepo.deleteById(reservationId);
            List<Reservation> resQueue = resRepo
                    .findAllByBookIdOrderByQueuePositionAsc(resToDelete.getBookId());

            List<Reservation> adjustedReservations =
                    resQueue.subList(queuePosition.intValue()-1, resQueue.size())
                    .stream()
                    .map(res -> {
                        res.setQueuePosition(queuePosition.getAndIncrement());
                        if (res.getQueuePosition() == 1 && resToDelete.getCollectBy() != null) {
                            res.setCollectBy(LocalDate.now().plusDays(3));
                        }
                        return res;
                    }).collect(Collectors.toList());
            resRepo.saveAll(adjustedReservations);
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

    @Transactional
    public void updateReservations(LocalDate currentDate) {
        try (Stream<Reservation> reservations = resRepo.findOverdueReservations(currentDate)) {
            reservations.forEach(reservation -> delete(reservation.getId()));
        }
    }
}
