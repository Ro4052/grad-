package com.scottlogic.librarygradproject;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

import static org.junit.Assert.*;

public class BookRepositoryTest {

    BookRepository repo;
    Book correctBook1 = new Book("0123456789", "Correct Book", "Correct Author", "1999");
    Book correctBook2 = new Book("0123456789", "Correct Book", "Correct Author", "1999");
    Book correctBook3 = new Book("0123456789", "Correct Book", "Correct Author", "1999");

    @Before
    public void before_Each_Test() {
        repo = new BookRepository();
    }

    @Test
    public void new_BookRepository_Is_Empty() {

        // Act
        List<Book> books = repo.getAll();

        // Assert
        assertTrue(books.isEmpty());
    }

    @Test
    public void add_Inserts_New_Book() {
        // Act
        repo.add(correctBook1);
        List<Book> books = repo.getAll();

        // Assert
        assertArrayEquals(new Book[] {correctBook1}, books.toArray());
    }

    @Test
    public void add_Sets_New_Id() {
        // Act
        repo.add(correctBook1);
        List<Book> books = repo.getAll();

        // Assert
        assertEquals(0, books.get(0).getId());
    }

    @Test(expected = IncorrectBookFormatException.class)
    public void add_With_Incorrect_BookISBN_Throws() {
        Book newBook = new Book("012345678", "Correct Book", "Correct Author", "1999");
        repo.add(newBook);
    }

    @Test(expected = IncorrectBookFormatException.class)
    public void add_With_Empty_BookTitle_Throws() {
        Book newBook = new Book("012345678", "", "Correct Author", "1999");
        repo.add(newBook);
    }

    @Test(expected = IncorrectBookFormatException.class)
    public void add_With_TooLong_BookTitle_Throws() {
        String longTitle = StringUtils.repeat("A", 201);
        Book newBook = new Book("012345678", longTitle, "Correct Author", "1999");
        repo.add(newBook);
    }

    @Test(expected = IncorrectBookFormatException.class)
    public void add_With_Empty_BookAuthor_Throws() {
        Book newBook = new Book("012345678", "1", "", "1999");
        repo.add(newBook);
    }

    @Test(expected = IncorrectBookFormatException.class)
    public void add_With_TooLong_BookAuthor_Throws() {
        String longAuthor = StringUtils.repeat("A", 201);
        Book newBook = new Book("012345678", "1", longAuthor, "1999");
        repo.add(newBook);
    }


    @Test(expected = IncorrectBookFormatException.class)
    public void add_With_Incorrect_BookPublishDate_Throws() {
        Book newBook = new Book("012345678", "Correct Book", "Correct Author", "19991");
        repo.add(newBook);
    }

    @Test
    public void get_Returns_Specific_Books() {
        repo.add(correctBook1);
        repo.add(correctBook2);

        // Act
        Book book = repo.get(1);

        // Assert
        assertEquals(correctBook2, book);
    }

    @Test
    public void getAll_Returns_All_Books() {
        repo.add(correctBook1);
        repo.add(correctBook2);

        // Act
        List<Book> books = repo.getAll();

        // Assert
        assertArrayEquals(new Book[] { correctBook1, correctBook2 }, books.toArray());
    }

    @Test
    public void delete_Removes_Correct_Book() {
        repo.add(correctBook1);
        repo.add(correctBook2);
        repo.add(correctBook3);

        // Act
        repo.remove(1);
        List<Book> books = repo.getAll();

        // Assert
        assertArrayEquals(new Book[] { correctBook1, correctBook3 }, books.toArray());
    }
}
