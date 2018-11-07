package com.scottlogic.librarygradproject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookRepository implements Repository<Book> {

    private int id = 0;

    private List<Book> bookCollection = new ArrayList<>();

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

    @Override
    public Book get(int id) {
        return bookCollection.stream().filter(book -> book.getId() == id).findFirst().orElseThrow(() -> new BookNotFoundException(id));
    }

    @Override
    public List<Book> getAll() {
        return bookCollection;
    }

    @Override
    public void add(Book book) {
        Book newBook = this.validateBook(book);
        newBook.setId(bookCollection.size());
        bookCollection.add(newBook);

    }

    @Override
    public void remove(int id) {
        Book bookToRemove = get(id);
        bookCollection.remove(bookToRemove);
    }

    @Override
    public void update(Book entity, int id) {
        Book updatedBook = this.validateBook(entity);
        final BooleanWrapper updated = new BooleanWrapper(false);
        bookCollection.stream().forEach(book -> {
            if (book.getId() == id) {
                book.setTitle(updatedBook.getTitle());
                book.setAuthor(updatedBook.getAuthor());
                book.setIsbn(updatedBook.getIsbn());
                book.setPublishDate(updatedBook.getPublishDate());
                updated.setBool(true);
            }
        });
        if (!updated.getBool()) {
            throw new BookNotFoundException(id);
        }
    }

    public void removeMultiple(List<Integer> ids) {
        List<Book> validBooks = new ArrayList<>();
        List<Integer> invalidBooks = new ArrayList<>();

        ids.forEach(id -> {
            try {
                Book validBook = get(id);
                validBooks.add(validBook);
            }
            catch(Exception BookNotFoundException) {
                invalidBooks.add((id));
            }
        });

        validBooks.forEach(book -> {
            bookCollection.remove(book);
        });
        if (invalidBooks.size() > 0) {
            throw new BookNotFoundException(invalidBooks);
        }
    }
}
