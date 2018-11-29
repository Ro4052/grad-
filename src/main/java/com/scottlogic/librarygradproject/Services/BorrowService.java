package com.scottlogic.librarygradproject.Services;

import com.scottlogic.librarygradproject.Entities.Book;
import com.scottlogic.librarygradproject.Entities.Borrow;
import com.scottlogic.librarygradproject.Entities.Reservation;
import com.scottlogic.librarygradproject.Exceptions.BookAlreadyBorrowedException;
import com.scottlogic.librarygradproject.Exceptions.BookAlreadyReturnedException;
import com.scottlogic.librarygradproject.Exceptions.BorrowNotFoundException;
import com.scottlogic.librarygradproject.LongWrapper;
import com.scottlogic.librarygradproject.Repositories.BorrowRepository;
import com.scottlogic.librarygradproject.Repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

public class BorrowService {

    private final BorrowRepository borrowRepository;
    private final UserService userService;
    private final BookService bookService;
    private final ReservationRepository reservationRepository;

    @Autowired
    public BorrowService(BorrowRepository borrowRepository, UserService userService, BookService bookService,
                         ReservationRepository reservationRepository) {
        this.borrowRepository = borrowRepository;
        this.userService = userService;
        this.bookService = bookService;
        this.reservationRepository = reservationRepository;
    }

    public long borrow(long bookId, OAuth2Authentication authentication) {
        if (this.isBorrowed(bookId)) throw new BookAlreadyBorrowedException(bookId);
        String userId = ((Map<String, String>) authentication.getUserAuthentication().getDetails()).get("login");
        LocalDate borrowDate = LocalDate.now();
        LocalDate returnDate = LocalDate.now().plusDays(7);
        Borrow loan = Borrow.builder().bookId(bookId).userId(userId).isActive(true).borrowDate(borrowDate).returnDate(returnDate).build();
        return borrowRepository.save(loan).getId();
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
        return borrowRepository.isBookBorrowed(bookId);
    }

    public boolean existsByUserIdAndBookId(String userId, long bookId) {
        return borrowRepository.existsByUserIdAndBookId(userId, bookId);
    }

    @Transactional
    public void bookReturned(long borrowId) {
        Borrow borrowToReturn = findOne(borrowId);
        if (!borrowToReturn.isActive()) {
            throw new BookAlreadyReturnedException(borrowId);
        }
        borrowToReturn.setActive(false);
        long bookId = borrowToReturn.getBookId();
        if (isBorrowed(bookId)) {
            throw new BookAlreadyBorrowedException(bookId);
        }
        List<Reservation> reservations = reservationRepository.findAllByBookIdOrderByQueuePositionAsc(bookId);
        if (!reservations.isEmpty()) {
            Reservation firstReservation = reservations.remove(0);
            borrowRepository.save(Borrow.builder()
                    .bookId(bookId)
                    .userId(firstReservation.getUserId())
                    .isActive(true)
                    .borrowDate(LocalDate.now())
                    .returnDate(LocalDate.now().plusDays(7))
                    .build());
            reservationRepository.delete(firstReservation);
        }
        final LongWrapper queuePosition = new LongWrapper(1);
        reservations.forEach(reservation -> {
                   reservation.setQueuePosition(queuePosition.getValue());
                   queuePosition.increment();
        });
    }

    @Transactional
    public void updateBorrowed(LocalDate currentDate) {
        List<Long> borrowIds = new ArrayList<>();
        try (Stream<Borrow> borrows = borrowRepository.findOverdueBorrows(currentDate)) {
            borrows.forEach(borrow -> borrowIds.add(borrow.getId()));
        }
        borrowIds.forEach(id -> bookReturned(id));
    }
}
