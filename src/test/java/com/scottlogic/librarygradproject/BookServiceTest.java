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
    public void update_Updates_Correct_Book() {

        // Arrange
        service.add(correctBook1);
        service.add(correctBook2);
        service.add(correctBook3);
        Book editedBook = Book.builder().isbn("1010101010").title("new Title").publishDate("1027").author("New Author").build();
        editedBook.setId(1);

        // Act
        service.update(editedBook);

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
