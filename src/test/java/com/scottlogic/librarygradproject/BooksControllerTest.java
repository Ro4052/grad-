package com.scottlogic.librarygradproject;

import org.junit.Test;

import static org.mockito.Mockito.*;

public class BooksControllerTest {

    @Test
    public void getAll_Calls_Repo_GetAll() {

        // Arrange
        BookRepository mockRepo = mock(BookRepository.class);
        BooksController controller = new BooksController(mockRepo);

        // Act
        controller.getAll();

        // Assert
        verify(mockRepo).getAll();
    }

    @Test
    public void get_With_Id_Calls_Repo_Get() {

        // Arrange
        int id = 1;
        BookRepository mockRepo = mock(BookRepository.class);
        BooksController controller = new BooksController(mockRepo);

        // Act
        controller.get(id);

        // Assert
        verify(mockRepo).get(id);
    }

    @Test
    public void post_With_Book_Calls_Repo_Add() {

        // Arrange
        BookRepository mockRepo = mock(BookRepository.class);
        BooksController controller = new BooksController(mockRepo);

        Book newBook = new Book();

        // Act
        controller.post(newBook);

        // Assert
        verify(mockRepo).add(newBook);
    }

    @Test
    public void delete_With_Id_Calls_Repo_Remove() {

        // Arrange
        int id = 1;
        BookRepository mockRepo = mock(BookRepository.class);
        BooksController controller = new BooksController(mockRepo);

        // Act
        controller.delete(id);

        // Assert
        verify(mockRepo).remove(id);
    }
}
