package com.scottlogic.librarygradproject.Services;

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

    private final BorrowRepository borrowRepo;
    private final BookService bookService;
    private final ReservationRepository reservationRepo;

    @Autowired
    public BorrowService(BorrowRepository borrowRepo, BookService bookService,
                         ReservationRepository reservationRepo) {
        this.borrowRepo = borrowRepo;
        this.bookService = bookService;
        this.reservationRepo = reservationRepo;
    }
    @SuppressWarnings("unchecked")
    public Borrow borrow(long bookId, OAuth2Authentication authentication) {
        if (this.isBorrowed(bookId)) throw new BookAlreadyBorrowedException(bookId);
        String userId = ((Map<String, String>) authentication.getUserAuthentication().getDetails()).get("login");
        LocalDate borrowDate = LocalDate.now();
        LocalDate returnDate = LocalDate.now().plusDays(7);
        Borrow loan = Borrow.builder().bookId(bookId).userId(userId).isActive(true).borrowDate(borrowDate).returnDate(returnDate).build();
        return borrowRepo.save(loan);
    }

    public List<Borrow> findAll() {
        return borrowRepo.findAll();
    }

    public Borrow findOne(long borrowId) {
        Optional<Borrow> borrowToGet = borrowRepo.findById(borrowId);
        return borrowToGet.orElseThrow(() -> new BorrowNotFoundException(borrowId));
    }

    public void delete(long borrowId) {
        try {
            borrowRepo.deleteById(borrowId);
        } catch (EmptyResultDataAccessException e) {
            throw new BorrowNotFoundException(borrowId);
        }
    }

    public boolean isBorrowed(long bookId) {
        bookService.findOne(bookId);
        return borrowRepo.isBookBorrowed(bookId) || reservationRepo.isBookAwaitingCollection(bookId);
    }

    public boolean existsByUserIdAndBookId(String userId, long bookId) {
        return borrowRepo.existsByUserIdAndBookIdAndIsActive(userId, bookId, true);
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
        Reservation reservationToCollect = reservationRepo.findOneByBookIdAndQueuePosition(bookId, 1);
        if (reservationToCollect != null) {
            reservationToCollect.setCollectBy(LocalDate.now().plusDays(3));
        }
    }

    @Transactional
    public void updateBorrowed(LocalDate currentDate) {
        try (Stream<Borrow> borrows = borrowRepo.findOverdueBorrows(currentDate)) {
            borrows.forEach(borrow -> bookReturned(borrow.getId()));
        }
    }
}
