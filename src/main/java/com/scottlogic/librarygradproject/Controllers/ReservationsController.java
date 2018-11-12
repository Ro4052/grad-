package com.scottlogic.librarygradproject.Controllers;

import com.scottlogic.librarygradproject.Entities.LibraryUser;
import com.scottlogic.librarygradproject.Services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")

public class ReservationsController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationsController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @RequestMapping(value = "/reserve/{bookId}", method = RequestMethod.POST)
    public void reserve(@PathVariable long bookId) {
        reservationService.reserve(bookId);
    }
}
