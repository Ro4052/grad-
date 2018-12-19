package com.scottlogic.librarygradproject;

import java.util.List;

public interface Repository<T> {
    T get(int id);
    List<T> getAll();
    void add(T entity);
    void remove(int id);
}
