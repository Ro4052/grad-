package com.scottlogic.librarygradproject;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class FilledBookRepositoryTest {

    @Test
    public void new_FilledBookRepository_Is_Full() {

        // Arrange
        FilledBookRepository repo = new FilledBookRepository();

        // Act
        List<Book> books = repo.getAll();

        // Assert
        assertThat(books.size(), is(4));

    }

    @Test
    public void new_FilledBookRepository_Has_Correct_Books() {

        // Arrange
        FilledBookRepository repo = new FilledBookRepository();
        Book newBook1=(Book.builder()
                .isbn("9780747532743")
                .title("Harry Potter and the Philosopher's Stone")
                .author("J. K. Rowling")
                .publishDate("1997")
                .build());
        Book newBook2 = (Book.builder()
                .isbn("9780194230476")
                .title("A Tale of Two Cities")
                .author("Charles Dickens")
                .publishDate("1935")
                .build());
        Book newBook3 = (Book.builder()
                .title("The Hobbit")
                .publishDate("1937")
                .isbn("1234567890")
                .author("J. R. R. Tolkien")
                .build());
        Book newBook4 = (Book.builder()
                .title("The Lion, the Witch and the Wardrobe")
                .publishDate("1950")
                .isbn("0987654321")
                .author("C. S. Lewis")
                .build());

        // Act
        List<Book> books = repo.getAll();
        List<Book> testList = new ArrayList<>(Arrays.asList(newBook1, newBook2, newBook3, newBook4));

        // Assert
        books.forEach((book) -> {
            assertThat(book.getIsbn(), is(testList.get(book.getId()).getIsbn()));
            assertThat(book.getPublishDate(), is(testList.get(book.getId()).getPublishDate()));
            assertThat(book.getAuthor(), is(testList.get(book.getId()).getAuthor()));
            assertThat(book.getTitle(), is(testList.get(book.getId()).getTitle()));
        });

    }
}
