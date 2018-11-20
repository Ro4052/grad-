package com.scottlogic.librarygradproject;

import com.scottlogic.librarygradproject.Controllers.ReservationsController;
import com.scottlogic.librarygradproject.Exceptions.BookNotFoundException;
import com.scottlogic.librarygradproject.Services.ReservationService;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ReservationsControllerTest {

    ReservationService reservationService;
    ReservationsController controller;

    @Before
    public void before_each_test() {
        reservationService = mock(ReservationService.class);
        controller = new ReservationsController(reservationService);
    }

    @Test
    public void post_calls_Repo_post() {
        //Arrange
        long id = 1;

        //Act
        controller.post(id);

        //Assert
        verify(reservationService).reserve(id);
    }

    @Test
    public void delete_calls_Repo_delete() {
        //Act
        controller.delete(1);

        //Assert
        verify(reservationService).delete(1);
    }

    @Test
    public void get_calls_Repo_findOne() {
        //Act
        controller.get(1);

        //Assert
        verify(reservationService).findOne(1);
    }

    @Test
    public void getAll_calls_Repo_findAll() {
        //Act
        controller.getAll();

        //Assert
        verify(reservationService).findAll();
    }

    @Test
    public void check_calls_Repo_CheckReservations() {
        //Arrange
        long bookId = 1;

        //Act
        controller.check(bookId);

        //Assert
        verify(reservationService).checkReservation(bookId);
    }

}
