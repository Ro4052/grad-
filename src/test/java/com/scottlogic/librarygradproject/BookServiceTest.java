package com.scottlogic.librarygradproject;

import com.scottlogic.librarygradproject.Entities.Book;
import com.scottlogic.librarygradproject.Exceptions.BookNotFoundException;
import com.scottlogic.librarygradproject.Exceptions.IncorrectBookFormatException;
import com.scottlogic.librarygradproject.Services.BookService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DataJpaTest
public class BookServiceTest {

    @Autowired
    BookService service;

    Book correctBook1 = new Book("0123456789111", "Correct Book1", "Correct Author1", "2001");
    Book correctBook2 = new Book("0123456789222", "Correct Book2", "Correct Author2", "2002");
    Book correctBook3 = new Book("0123456789333", "Correct Book3", "Correct Author3", "2003");

    @Test
    public void new_BookRepository_Is_Empty() {
        List<Book> books = service.findAll();
        assertTrue(books.isEmpty());
    }

    @Test
    public void add_Inserts_New_Book() {
        service.add(correctBook1);
        List<Book> books = service.findAll();
        assertArrayEquals(new Book[]{correctBook1}, books.toArray());
    }

    @Test
    public void add_With_Whitespaces_Trims_And_Inserts() {
        Book newBook = new Book("  1231231231231 ", "    Correct Book ", "  Correct Author  ", " 1999 ");
        service.add(newBook);
        Book trimmedBook = new Book("1231231231231", "Correct Book", "Correct Author", "1999");
        assertArrayEquals(new Book[]{trimmedBook}, service.findAll().toArray());
    }

    @Test(expected = IncorrectBookFormatException.class)
    public void add_With_Incorrect_BookISBN_Throws() {
        Book newBook = new Book("012345678", "Correct Book", "Correct Author", "1999");
        service.add(newBook);
    }

    @Test
    public void add_With_Null_BookISBN_Works() {
        Book newBook = new Book(null, "Correct Book", "Correct Author", "1999");
        service.add(newBook);
        assertArrayEquals(new Book[]{newBook}, service.findAll().toArray());
    }

    @Test
    public void add_With_Empty_BookISBN_Works() {
        Book newBook = new Book("", "Correct Book", "Correct Author", "1999");
        service.add(newBook);
        assertArrayEquals(new Book[]{newBook}, service.findAll().toArray());
    }

    @Test
    public void add_With_10Digits_BookISBN_Works() {
        Book newBook = new Book("1231231231", "Correct Book", "Correct Author", "1999");
        service.add(newBook);
        assertArrayEquals(new Book[]{newBook}, service.findAll().toArray());
    }

    @Test
    public void add_With_13Digits_BookISBN_Works() {
        Book newBook = new Book("1231231231231", "Correct Book", "Correct Author", "1999");
        service.add(newBook);
        assertArrayEquals(new Book[]{newBook}, service.findAll().toArray());
    }

    @Test(expected = IncorrectBookFormatException.class)
    public void add_With_9Digits_1Letter_BookISBN_Throws() {
        Book newBook = new Book("123123123A", "Correct Book", "Correct Author", "1999");
        service.add(newBook);
    }

    @Test(expected = IncorrectBookFormatException.class)
    public void add_With_12Digits_1Letter_BookISBN_Throws() {
        Book newBook = new Book("123123123123A", "Correct Book", "Correct Author", "1999");
        service.add(newBook);
    }

    @Test(expected = IncorrectBookFormatException.class)
    public void add_With_Empty_BookTitle_Throws() {
        Book newBook = new Book("012345678", "", "Correct Author", "1999");
        service.add(newBook);
    }

    @Test(expected = IncorrectBookFormatException.class)
    public void add_With_Null_BookTitle_Throws() {
        Book newBook = new Book("012345678", null, "Correct Author", "1999");
        service.add(newBook);
    }

    @Test(expected = IncorrectBookFormatException.class)
    public void add_With_TooLong_BookTitle_Throws() {
        String longTitle = StringUtils.repeat("A", 201);
        Book newBook = new Book("012345678", longTitle, "Correct Author", "1999");
        service.add(newBook);
    }

    @Test(expected = IncorrectBookFormatException.class)
    public void add_With_Empty_BookAuthor_Throws() {
        Book newBook = new Book("012345678", "1", "", "1999");
        service.add(newBook);
    }

    @Test(expected = IncorrectBookFormatException.class)
    public void add_With_Null_BookAuthor_Throws() {
        Book newBook = new Book("012345678", "1", null, "1999");
        service.add(newBook);
    }

    @Test(expected = IncorrectBookFormatException.class)
    public void add_With_TooLong_BookAuthor_Throws() {
        String longAuthor = StringUtils.repeat("A", 201);
        Book newBook = new Book("012345678", "1", longAuthor, "1999");
        service.add(newBook);
    }

    @Test(expected = IncorrectBookFormatException.class)
    public void add_With_Incorrect_BookPublishDate_Throws() {
        Book newBook = new Book("012345678", "Correct Book", "Correct Author", "19991");
        service.add(newBook);
    }

    @Test
    public void get_Returns_Specific_Books() {
        service.add(correctBook1);
        service.add(correctBook2);

        Book book = service.findOne(2L);
        assertEquals(correctBook2, book);
    }

    @Test
    public void getAll_Returns_All_Books() {
        service.add(correctBook1);
        service.add(correctBook2);
        List<Book> books = service.findAll();
        assertArrayEquals(new Book[]{correctBook1, correctBook2}, books.toArray());
    }

    @Test(expected = BookNotFoundException.class)
    public void get_Throws_Invalid_Id() {
        service.add(correctBook1);
        service.add(correctBook2);
        service.findOne(4L);
    }


    @Test
    public void delete_Removes_Correct_Book() {
        service.add(correctBook1);
        service.add(correctBook2);
        service.add(correctBook3);
        service.delete(2L);
        List<Book> books = service.findAll();
        assertArrayEquals(new Book[]{correctBook1, correctBook3}, books.toArray());
    }

    @Test
    public void put_Updates_Correct_Book() {

        // Arrange
        service.add(correctBook1);
        service.add(correctBook2);
        service.add(correctBook3);
        Book editedBook = Book.builder().isbn("1010101010").title("new Title").publishDate("1027").author("New Author").build();
        editedBook.setId(1);

        // Act
        service.put(editedBook);

        // Assert
        assertThat(service.findOne(1).getTitle(), is(editedBook.getTitle()));
        assertThat(service.findOne(1).getAuthor(), is(editedBook.getAuthor()));
        assertThat(service.findOne(1).getPublishDate(), is(editedBook.getPublishDate()));
        assertThat(service.findOne(1).getIsbn(), is(editedBook.getIsbn()));
    }

    @Test
    public void delete_Multiple_Correct_Books() {

        // Arrange
        service.add(correctBook1);
        service.add(correctBook2);
        service.add(correctBook3);
        List<Long> ids = new ArrayList<Long>();
        ids.add(1L);
        ids.add(3L);

        // Act
        service.removeMultiple(ids);
        List<Book> books = service.findAll();

        // Assert
        assertArrayEquals(new Book[] { correctBook2 }, books.toArray());
    }

    @Test
    public void delete_Multiple_Rejects_Invalid_Books() {

        // Arrange
        service.add(correctBook1);
        service.add(correctBook2);
        service.add(correctBook3);
        List<Long> ids = new ArrayList<Long>();
        ids.add(5L);
        ids.add(2L);

        // Act
        try {
            service.removeMultiple(ids);
        }
        catch(Exception BookNotFoundException) {
            assertThat(BookNotFoundException.getMessage(), is("Could not find book ids: 5"));
        }

        // Assert
        List<Book> books = service.findAll();
        assertArrayEquals(new Book[] {correctBook1, correctBook3}, books.toArray());
    }
}
