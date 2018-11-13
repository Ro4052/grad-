package com.scottlogic.librarygradproject.Services;

import com.scottlogic.librarygradproject.Entities.Book;
import com.scottlogic.librarygradproject.Exceptions.BookNotFoundException;
import com.scottlogic.librarygradproject.Repositories.BookRepository;
import com.scottlogic.librarygradproject.Exceptions.IncorrectBookFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public class BookService {

    private final BookRepository bookRepo;

    @Autowired
    public BookService(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    private Book validateBook(Book book) {
        book.setIsbn(Optional.ofNullable(book.getIsbn()).orElse("").trim());
        book.setAuthor(Optional.ofNullable(book.getAuthor()).orElseThrow(() -> new IncorrectBookFormatException()).trim());
        book.setTitle(Optional.ofNullable(book.getTitle()).orElseThrow(() -> new IncorrectBookFormatException()).trim());
        book.setPublishDate(Optional.ofNullable(book.getPublishDate()).orElse("").trim());
        String year = book.getPublishDate();
        if (year.length() > 0) {
            while (year.length() < 4) {
                year = "0" + year;
            }
        }
        book.setPublishDate(year);
        if (!book.getIsbn().matches("|[0-9]{10}|[0-9]{13}") ||
                (book.getAuthor().length() == 0 || book.getAuthor().length() > 200) ||
                (book.getTitle().length() == 0 || book.getTitle().length() > 200) ||
                (book.getPublishDate().length() > 0 && (!book.getPublishDate().matches("|[0-9]{4}") ||
                        Integer.parseInt(book.getPublishDate()) > Calendar.getInstance().get(Calendar.YEAR)))) {
            throw new IncorrectBookFormatException();
        }
        return book;
    }

    public List<Book> findAll() {
        return bookRepo.findAll();
    }

    public Book findOne(long id) {
        Optional<Book> bookToGet = Optional.ofNullable(bookRepo.findOne(id));
        return bookToGet.orElseThrow(() -> new BookNotFoundException(id));
    }

    public void delete(long id) {
        try {
            bookRepo.delete(id);
        } catch (EmptyResultDataAccessException e) {
            throw new BookNotFoundException(id);
        }
    }

    public void save(Book book) {
        Book newBook = validateBook(book);
        bookRepo.insert(newBook);
    }

    public void put(Book book) {
        findOne(book.getId());
        Book updatedBook = validateBook(book);
        bookRepo.save(updatedBook);
    }

    public void deleteAll() {
        bookRepo.deleteAll();
    }

    public void removeMultiple(List<Long> ids) {
        List<Book> validBooks = bookRepo.findAll(ids);
        validBooks.forEach(book -> ids.remove(book.getId()));
        bookRepo.delete(validBooks);
        if (ids.size() > 0) {
            throw new BookNotFoundException(ids);
        }
    }
}
