package com.scottlogic.librarygradproject.Services;

import com.scottlogic.librarygradproject.Entities.Borrow;
import com.scottlogic.librarygradproject.Entities.Reservation;
import com.scottlogic.librarygradproject.Exceptions.*;
import com.scottlogic.librarygradproject.Repositories.BorrowRepository;
import com.scottlogic.librarygradproject.Repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class BorrowService {

    private final BorrowRepository borrowRepository;
    private final BookService bookService;
    private final ReservationRepository reservationRepository;

    @Autowired
    public BorrowService(BorrowRepository borrowRepository, BookService bookService,
                         ReservationRepository reservationRepository) {
        this.borrowRepository = borrowRepository;
        this.bookService = bookService;
        this.reservationRepository = reservationRepository;
    }
    @SuppressWarnings("unchecked")
    public Borrow borrow(long bookId, OAuth2Authentication authentication) {
        if (this.isBorrowed(bookId)) throw new BookAlreadyBorrowedException(bookId);
        String userId = ((Map<String, String>) authentication.getUserAuthentication().getDetails()).get("login");
        LocalDate borrowDate = LocalDate.now();
        LocalDate returnDate = LocalDate.now().plusDays(7);
        Borrow loan = Borrow.builder().bookId(bookId).userId(userId).isActive(true).borrowDate(borrowDate).returnDate(returnDate).build();
        return borrowRepository.save(loan);
    }

    public List<Borrow> findAll() {
        return borrowRepository.findAll();
    }

    public Borrow findOne(long borrowId) {
        Optional<Borrow> borrowToGet = borrowRepository.findById(borrowId);
        return borrowToGet.orElseThrow(() -> new BorrowNotFoundException(borrowId));
    }

    public void delete(long borrowId) {
        try {
            borrowRepository.deleteById(borrowId);
        } catch (EmptyResultDataAccessException e) {
            throw new BorrowNotFoundException(borrowId);
        }
    }

    public boolean isBorrowed(long bookId) {
        bookService.findOne(bookId);
        return borrowRepository.isBookBorrowed(bookId) || reservationRepository.isBookAwaitingCollection(bookId);
    }

    public boolean existsByUserIdAndBookId(String userId, long bookId) {
        return borrowRepository.existsByUserIdAndBookIdAndIsActive(userId, bookId, true);
    }

    @Transactional
    public void bookReturned(long borrowId) {
        Borrow borrowToReturn = findOne(borrowId);
        if (!borrowToReturn.isActive()) {
            throw new BookAlreadyReturnedException(borrowId);
        }
        borrowToReturn.setActive(false);
        borrowToReturn.setReturnDate(LocalDate.now());
        long bookId = borrowToReturn.getBookId();
        if (isBorrowed(bookId)) {
            throw new BookAlreadyBorrowedException(bookId);
        }
        Reservation reservationToCollect = reservationRepository.findOneByBookIdAndQueuePosition(bookId, 1);
        if (reservationToCollect != null) {
            reservationToCollect.setCollectBy(LocalDate.now().plusDays(3));
        }
    }

    public Borrow bookCollected(long bookId) {
            List<Reservation> reservations = reservationRepository.findAllByBookIdOrderByQueuePositionAsc(bookId);
            if (reservations.isEmpty()) {throw new BookNotReservedException(bookId);}

            Reservation firstReservation = reservations.remove(0);

            Borrow borrow = (Borrow.builder()
                    .bookId(bookId)
                    .userId(firstReservation.getUserId())
                    .isActive(true)
                    .borrowDate(LocalDate.now())
                    .returnDate(LocalDate.now().plusDays(7))
                    .build());

            reservationRepository.delete(firstReservation);

            AtomicLong queuePosition = new AtomicLong(1);
            reservations.forEach(reservation -> {
                reservation.setQueuePosition(queuePosition.getAndIncrement());
                reservationRepository.save(reservation);
            });
            return borrowRepository.save(borrow);
    }

    @Transactional
    public void updateBorrowed(LocalDate currentDate) {
        try (Stream<Borrow> borrows = borrowRepository.findOverdueBorrows(currentDate)) {
            borrows.forEach(borrow -> bookReturned(borrow.getId()));
        }
    }
}
