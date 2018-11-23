package com.scottlogic.librarygradproject.Services;

import com.scottlogic.librarygradproject.Entities.Book;
import com.scottlogic.librarygradproject.Entities.Borrow;
import com.scottlogic.librarygradproject.Entities.Reservation;
import com.scottlogic.librarygradproject.Exceptions.BookAlreadyBorrowedException;
import com.scottlogic.librarygradproject.Exceptions.BookAlreadyReturnedException;
import com.scottlogic.librarygradproject.Exceptions.BorrowNotFoundException;
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

    public void borrow(long bookId, OAuth2Authentication authentication) {
        bookService.findOne(bookId);
        String userId = userService.loggedIn(authentication).getUserId();
        LocalDate borrowDate = LocalDate.now();
        LocalDate returnDate = LocalDate.now().plusDays(7);
        Borrow loan = Borrow.builder().bookId(bookId).userId(userId).isActive(true).borrowDate(borrowDate).returnDate(returnDate).build();
        borrowRepository.save(loan);
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

    @Transactional
    public void bookReturned(long borrowId) {
        Borrow borrowToReturn = findOne(borrowId);
        if (!borrowToReturn.isActive()) {
            throw new BookAlreadyReturnedException(borrowId);
        }
        borrowToReturn.setActive(false);
        long bookId = borrowToReturn.getBookId();
        List<Reservation> reservations = reservationRepository.findAllByBookId(bookId);
        reservations.forEach(reservation -> {
            if (reservation.getQueuePosition() == 1) {
                if (isBorrowed(bookId)) {
                    throw new BookAlreadyBorrowedException(reservation.getBookId());
                }
                borrowRepository.save(Borrow.builder()
                        .bookId(bookId)
                        .userId(reservation.getUserId())
                        .isActive(true)
                        .borrowDate(LocalDate.now())
                        .returnDate(LocalDate.now().plusDays(7))
                        .build());
                reservationRepository.delete(reservation);
            }
            else {
                reservation.setQueuePosition(reservation.getQueuePosition() - 1);
            }
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
