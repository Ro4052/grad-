package com.scottlogic.librarygradproject;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
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
    public void add_With_Whitespaces_Trims_And_Inserts() {
        Book newBook = new Book("  1231231231231 ", "    Correct Book ", "  Correct Author  ", " 1999 ");
        repo.add(newBook);
        Book trimmedBook = new Book("1231231231231", "Correct Book", "Correct Author", "1999");
        assertArrayEquals(new Book[] {trimmedBook}, repo.getAll().toArray() );
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

    @Test
    public void add_With_Null_BookISBN_Works() {
        Book newBook = new Book(null, "Correct Book", "Correct Author", "1999");
        repo.add(newBook);
        assertArrayEquals(new Book[] {newBook}, repo.getAll().toArray() );
    }

    @Test
    public void add_With_Empty_BookISBN_Works() {
        Book newBook = new Book("", "Correct Book", "Correct Author", "1999");
        repo.add(newBook);
        assertArrayEquals(new Book[] {newBook}, repo.getAll().toArray() );
    }

    @Test
    public void add_With_10Digits_BookISBN_Works() {
        Book newBook = new Book("1231231231", "Correct Book", "Correct Author", "1999");
        repo.add(newBook);
        assertArrayEquals(new Book[] {newBook}, repo.getAll().toArray() );
    }

    @Test
    public void add_With_13Digits_BookISBN_Works() {
        Book newBook = new Book("1231231231231", "Correct Book", "Correct Author", "1999");
        repo.add(newBook);
        assertArrayEquals(new Book[] {newBook}, repo.getAll().toArray() );
    }

    @Test(expected = IncorrectBookFormatException.class)
    public void add_With_9Digits_1Letter_BookISBN_Throws() {
        Book newBook = new Book("123123123A", "Correct Book", "Correct Author", "1999");
        repo.add(newBook);
    }

    @Test(expected = IncorrectBookFormatException.class)
    public void add_With_12Digits_1Letter_BookISBN_Throws() {
        Book newBook = new Book("123123123123A", "Correct Book", "Correct Author", "1999");
        repo.add(newBook);
    }



    @Test(expected = IncorrectBookFormatException.class)
    public void add_With_Empty_BookTitle_Throws() {
        Book newBook = new Book("012345678", "", "Correct Author", "1999");
        repo.add(newBook);
    }

    @Test(expected = IncorrectBookFormatException.class)
    public void add_With_Null_BookTitle_Throws() {
        Book newBook = new Book("012345678", null, "Correct Author", "1999");
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
    public void add_With_Null_BookAuthor_Throws() {
        Book newBook = new Book("012345678", "1", null, "1999");
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

    @Test
    public void update_Updates_Correct_Book() {

        // Arrange
        FilledBookRepository repo = new FilledBookRepository();
        Book editedBook = Book.builder().isbn("1010101010").title("new Title").publishDate("1027").author("New Author").build();

        // Act
        repo.update(editedBook, 1);

        // Assert
        assertThat(repo.get(1).getTitle(), is(editedBook.getTitle()));
        assertThat(repo.get(1).getAuthor(), is(editedBook.getAuthor()));
        assertThat(repo.get(1).getPublishDate(), is(editedBook.getPublishDate()));
        assertThat(repo.get(1).getIsbn(), is(editedBook.getIsbn()));
    }
}
