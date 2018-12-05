package com.scottlogic.librarygradproject.Services;

import com.scottlogic.librarygradproject.Entities.Book;
import com.scottlogic.librarygradproject.Exceptions.BookNotFoundException;
import com.scottlogic.librarygradproject.Exceptions.IncorrectBookFormatException;
import com.scottlogic.librarygradproject.Repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import java.util.List;
import java.util.Optional;

public class BookService {

    private final BookRepository bookRepo;

    @Autowired
    public BookService(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
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
            bookRepo.clearDeletedBookReservations();
            bookRepo.clearDeletedBookBorrows();

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
        bookRepo.clearDeletedBookReservations();
        bookRepo.clearDeletedBookBorrows();
    }

    public void removeMultiple(List<Long> ids) {
        List<Book> validBooks = bookRepo.findAllById(ids);
        validBooks.forEach(book -> ids.remove(book.getId()));
        bookRepo.deleteAll(validBooks);
        bookRepo.clearDeletedBookReservations();
        bookRepo.clearDeletedBookBorrows();

        if (ids.size() > 0) {
            throw new BookNotFoundException(ids);
        }
    }
}
