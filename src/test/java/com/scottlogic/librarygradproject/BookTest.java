package com.scottlogic.librarygradproject;

import com.scottlogic.librarygradproject.Entities.Book;
import com.scottlogic.librarygradproject.Exceptions.IncorrectBookFormatException;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BookTest {
    @Test
    public void create_With_Whitespaces_Trims() {
        Book newBook = new Book("  1231231231231 ", "    Correct Book ", "  Correct Author  ", " 1999 ");
        assertEquals("1231231231231", newBook.getIsbn());
        assertEquals("Correct Book", newBook.getTitle());
        assertEquals("Correct Author", newBook.getAuthor());
        assertEquals("1999", newBook.getPublishDate());
    }

    @Test(expected = IncorrectBookFormatException.class)
    public void create_With_Incorrect_BookISBN_Throws() {
        Book newBook = new Book("012345678", "Correct Book", "Correct Author", "1999");
    }

    @Test
    public void create_With_Null_BookISBN_Works() {
        Book newBook = new Book(null, "Correct Book", "Correct Author", "1999");
        assertEquals("", newBook.getIsbn());
    }

    @Test
    public void create_With_Empty_BookISBN_Works() {
        Book newBook = new Book("", "Correct Book", "Correct Author", "1999");
        assertEquals("", newBook.getIsbn());
    }

    @Test
    public void create_With_10Digits_BookISBN_Works() {
        Book newBook = new Book("1231231231", "Correct Book", "Correct Author", "1999");
        assertEquals("1231231231", newBook.getIsbn());
    }

    @Test
    public void create_With_13Digits_BookISBN_Works() {
        Book newBook = new Book("1231231231231", "Correct Book", "Correct Author", "1999");
        assertEquals("1231231231231", newBook.getIsbn());
    }

    @Test(expected = IncorrectBookFormatException.class)
    public void create_With_9Digits_1Letter_BookISBN_Throws() {
        Book newBook = new Book("123123123A", "Correct Book", "Correct Author", "1999");
    }

    @Test(expected = IncorrectBookFormatException.class)
    public void create_With_12Digits_1Letter_BookISBN_Throws() {
        Book newBook = new Book("123123123123A", "Correct Book", "Correct Author", "1999");
    }

    @Test(expected = IncorrectBookFormatException.class)
    public void create_With_Empty_BookTitle_Throws() {
        Book newBook = new Book("012345678", "", "Correct Author", "1999");
    }

    @Test(expected = IncorrectBookFormatException.class)
    public void create_With_Null_BookTitle_Throws() {
        Book newBook = new Book("012345678", null, "Correct Author", "1999");
    }

    @Test(expected = IncorrectBookFormatException.class)
    public void create_With_TooLong_BookTitle_Throws() {
        String longTitle = StringUtils.repeat("A", 201);
        Book newBook = new Book("012345678", longTitle, "Correct Author", "1999");
    }

    @Test(expected = IncorrectBookFormatException.class)
    public void create_With_Empty_BookAuthor_Throws() {
        Book newBook = new Book("012345678", "1", "", "1999");
    }

    @Test(expected = IncorrectBookFormatException.class)
    public void create_With_Null_BookAuthor_Throws() {
        Book newBook = new Book("012345678", "1", null, "1999");
    }

    @Test(expected = IncorrectBookFormatException.class)
    public void create_With_TooLong_BookAuthor_Throws() {
        String longAuthor = StringUtils.repeat("A", 201);
        Book newBook = new Book("012345678", "1", longAuthor, "1999");
    }

    @Test(expected = IncorrectBookFormatException.class)
    public void create_With_Incorrect_BookPublishDate_Throws() {
        Book newBook = new Book("012345678", "Correct Book", "Correct Author", "19991");
    }
}
