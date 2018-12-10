package com.scottlogic.librarygradproject.Helpers;

import com.scottlogic.librarygradproject.Exceptions.BookNotFoundException;
import com.scottlogic.librarygradproject.Repositories.BookRepository;
import com.scottlogic.librarygradproject.Repositories.BorrowRepository;
import com.scottlogic.librarygradproject.Repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class BorrowHelper {

    private final BookRepository bookRepo;
    private final BorrowRepository borrowRepo;
    private final ReservationRepository reservationRepo;

    @Autowired
    public BorrowHelper(BookRepository bookRepo, BorrowRepository borrowRepo, ReservationRepository reservationRepo) {
        this.bookRepo = bookRepo;
        this.borrowRepo = borrowRepo;
        this.reservationRepo = reservationRepo;
    }

    public boolean isBorrowed(long bookId) {
        if (bookRepo.existsById(bookId)) {
            return borrowRepo.isBookBorrowed(bookId) || reservationRepo.isBookAwaitingCollection(bookId);
        } else {
            throw new BookNotFoundException(bookId);
        }
    }
}
