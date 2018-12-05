package com.scottlogic.librarygradproject;

import com.scottlogic.librarygradproject.Controllers.BooksController;
import com.scottlogic.librarygradproject.Entities.Book;
import com.scottlogic.librarygradproject.Services.BookService;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class BooksControllerTest {

    BookService bookService;
    BooksController controller;

    @Before
    public void before_Each_Test() {
        bookService = mock(BookService.class);
        controller = new BooksController(bookService);
    }

    @Test
    public void getAll_Calls_Repo_GetAll() {
        // Act
        controller.getAll();

        // Assert
        verify(bookService).findAll();
    }

    @Test
    public void get_With_Id_Calls_Repo_Get() {

        // Arrange
        int id = 1;
        // Act
        controller.get(id);

        // Assert
        verify(bookService).findOne(id);
    }

    @Test
    public void post_With_Book_Calls_Repo_Add() {

        Book newBook = new Book();

        // Act
        controller.post(newBook);

        // Assert
        verify(bookService).add(newBook);
    }

    @Test
    public void delete_With_Id_Calls_Repo_Remove() {

        // Arrange
        int id = 1;

        // Act
        controller.delete(id);

        // Assert
        verify(bookService).delete(id);
    }
}
