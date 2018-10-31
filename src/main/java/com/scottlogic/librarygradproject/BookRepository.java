package com.scottlogic.librarygradproject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookRepository implements Repository<Book> {

    private int id = 0;

    private List<Book> bookCollection = new ArrayList<>();

    @Override
    public Book get(int id) {
        return bookCollection.stream().filter(book -> book.getId() == id).findFirst().get();
    }

    @Override
    public List<Book> getAll() {
        return bookCollection;
    }

    @Override
    public void add(Book book) {
        if (book.getIsbn() == null) {book.setIsbn("");}
        if (book.getTitle() == null) {book.setTitle("");}
        if (book.getAuthor() == null) {book.setAuthor("");}
        if (book.getPublishDate() == null) {book.setPublishDate("");}
        book.setIsbn(book.getIsbn().trim());
        book.setAuthor(book.getAuthor().trim());
        book.setTitle(book.getTitle().trim());
        book.setPublishDate(book.getPublishDate().trim());
        if (!book.getIsbn().matches("|[0-9]{10}|[0-9]{13}") ||
                (book.getAuthor().length() == 0 || book.getAuthor().length() > 200) ||
                (book.getTitle().length() == 0 || book.getTitle().length() > 200) ||
                (!book.getPublishDate().matches("|[0-9]{4}"))) {
            throw new IncorrectBookFormatException();
        }
        book.setId(bookCollection.size());
        bookCollection.add(book);

    }

    @Override
    public void remove(int id) {
        Book bookToRemove = get(id);
        bookCollection.remove(bookToRemove);
    }

    @Override
    public void update(Book entity, int id) {
        final BooleanWrapper updated = new BooleanWrapper(false);
        bookCollection.stream().forEach(book -> {
            if (book.getId() == id) {
                book.setTitle(entity.getTitle());
                book.setAuthor(entity.getAuthor());
                book.setIsbn(entity.getIsbn());
                book.setPublishDate(entity.getPublishDate());
                updated.setBool(true);
            }
        });

        if (!updated.getBool()) {
            throw new BookNotFoundException(id);
        }
    }
}
