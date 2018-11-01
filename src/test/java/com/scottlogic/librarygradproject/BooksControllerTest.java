package com.scottlogic.librarygradproject;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class BooksControllerTest {

    FilledBookRepository mockRepo;
    BooksController controller;

    @Before
    public void before_Each_Test() {
        mockRepo = mock(FilledBookRepository.class);
        controller = new BooksController(mockRepo);
    }

    @Test
    public void getAll_Calls_Repo_GetAll() {
        // Act
        controller.getAll();

        // Assert
        verify(mockRepo).getAll();
    }

    @Test
    public void get_With_Id_Calls_Repo_Get() {

        // Arrange
        int id = 1;
        // Act
        controller.get(id);

        // Assert
        verify(mockRepo).get(id);
    }

    @Test
    public void post_With_Book_Calls_Repo_Add() {

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

        // Act
        controller.delete(id);

        // Assert
        verify(mockRepo).remove(id);
    }
}
