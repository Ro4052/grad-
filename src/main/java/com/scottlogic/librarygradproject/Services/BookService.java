package com.scottlogic.librarygradproject.Services;

import com.scottlogic.librarygradproject.Entities.Book;
import com.scottlogic.librarygradproject.Exceptions.BookNotFoundException;
import com.scottlogic.librarygradproject.Exceptions.IncorrectBookFormatException;
import com.scottlogic.librarygradproject.Repositories.BookRepository;
import com.scottlogic.librarygradproject.Repositories.BorrowRepository;
import com.scottlogic.librarygradproject.Repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import java.util.List;
import java.util.Optional;

public class BookService {

    private final BookRepository bookRepo;
    private final BorrowRepository borrowRepo;
    private final ReservationRepository reservationRepo;

    @Autowired
    public BookService(BookRepository bookRepo, BorrowRepository borrowRepo, ReservationRepository reservationRepo) {
        this.bookRepo = bookRepo;
        this.borrowRepo = borrowRepo;
        this.reservationRepo = reservationRepo;
    }

    public List<Book> findAll() {
        return bookRepo.findAll();
    }

    public Book findOne(long id) {
        Optional<Book> bookToGet = bookRepo.findById(id);
        return bookToGet.orElseThrow(() -> new BookNotFoundException(id));
    }

    public void delete(long id) {
        try {
            bookRepo.deleteById(id);
            reservationRepo.deleteByBookId(id);
            borrowRepo.deactivateByBookId(id);

        } catch (EmptyResultDataAccessException e) {
            throw new BookNotFoundException(id);
        }
    }

    public void add(Book book) {
        if (book.getId() != 0) {
            throw new IncorrectBookFormatException();
        }
        bookRepo.save(book);
    }

    public void update(Book book) {
        long bookId = book.getId();
        if (bookRepo.existsById(bookId)) {
            bookRepo.save(book);
        } else {
            throw new BookNotFoundException(bookId);
        }
    }

    public void deleteAll() {
        bookRepo.deleteAll();
        reservationRepo.deleteAll();
        borrowRepo.deactivateAll();
    }

    public void removeMultiple(List<Long> ids) {
        List<Book> validBooks = bookRepo.findAllById(ids);
        reservationRepo.deleteByMultipleIds(ids);
        borrowRepo.deactivateByMultipleIds(ids);
        validBooks.forEach(book -> ids.remove(book.getId()));
        bookRepo.deleteAll(validBooks);

        if (ids.size() > 0) {
            throw new BookNotFoundException(ids);
        }
    }
}
