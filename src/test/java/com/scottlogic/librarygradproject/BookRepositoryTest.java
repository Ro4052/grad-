package com.scottlogic.librarygradproject;

import org.junit.Test;
import java.util.List;

import static org.junit.Assert.*;

public class BookRepositoryTest {

    @Test
    public void new_BookRepository_Is_Empty() {

        // Arrange
        BookRepository repo = new BookRepository();

        // Act
        List<Book> books = repo.getAll();

        // Assert
        assertTrue(books.isEmpty());
    }

    @Test
    public void add_Inserts_New_Book() {

        // Arrange
        BookRepository repo = new BookRepository();
        Book newBook = new Book();

        // Act
        repo.add(newBook);
        List<Book> books = repo.getAll();

        // Assert
        assertArrayEquals(new Book[] {newBook}, books.toArray());
    }

    @Test
    public void add_Sets_New_Id() {

        // Arrange
        BookRepository repo = new BookRepository();
        Book newBook = new Book();

        // Act
        repo.add(newBook);
        List<Book> books = repo.getAll();

        // Assert
        assertEquals(0, books.get(0).getId());
    }

    @Test
    public void get_Returns_Specific_Books() {

        // Arrange
        BookRepository repo = new BookRepository();
        Book newBook1 = new Book();
        Book newBook2 = new Book();
        repo.add(newBook1);
        repo.add(newBook2);

        // Act
        Book book = repo.get(1);

        // Assert
        assertEquals(newBook2, book);
    }

    @Test
    public void getAll_Returns_All_Books() {

        // Arrange
        BookRepository repo = new BookRepository();
        Book newBook1 = new Book();
        Book newBook2 = new Book();
        repo.add(newBook1);
        repo.add(newBook2);

        // Act
        List<Book> books = repo.getAll();

        // Assert
        assertArrayEquals(new Book[] { newBook1, newBook2 }, books.toArray());
    }

    @Test
    public void delete_Removes_Correct_Book() {

        // Arrange
        BookRepository repo = new BookRepository();
        Book newBook1 = new Book();
        Book newBook2 = new Book();
        Book newBook3 = new Book();
        repo.add(newBook1);
        repo.add(newBook2);
        repo.add(newBook3);

        // Act
        repo.remove(1);
        List<Book> books = repo.getAll();

        // Assert
        assertArrayEquals(new Book[] { newBook1, newBook3 }, books.toArray());
    }
}
