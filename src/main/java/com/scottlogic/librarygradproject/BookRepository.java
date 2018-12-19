package com.scottlogic.librarygradproject;

import java.util.ArrayList;
import java.util.List;

public class BookRepository implements Repository<Book> {

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
    public void add(Book entity) {
        entity.setId(bookCollection.size());
        bookCollection.add(entity);
    }

    @Override
    public void remove(int id) {
        Book bookToRemove = get(id);
        bookCollection.remove(bookToRemove);
    }
}
