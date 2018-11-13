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

}
