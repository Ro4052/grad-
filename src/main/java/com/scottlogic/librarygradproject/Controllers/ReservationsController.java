package com.scottlogic.librarygradproject.Controllers;

import com.scottlogic.librarygradproject.Entities.LibraryUser;
import com.scottlogic.librarygradproject.Entities.Reservation;
import com.scottlogic.librarygradproject.Services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")

public class ReservationsController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationsController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @RequestMapping(value = "/reserve/{bookId}", method = RequestMethod.POST)
    public void post(@PathVariable long bookId) {
        reservationService.reserve(bookId);
    }

    @RequestMapping(value = "/reserve/{reservationId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable long reservationId) { reservationService.delete(reservationId); }

    @RequestMapping(value = "/reserve", method = RequestMethod.GET)
    public List<Reservation> getAll() { return reservationService.findAll(); }

    @RequestMapping(value = "/reserve/{reservationId}", method = RequestMethod.GET)
    public Reservation get(@PathVariable long reservationId) { return reservationService.findOne(reservationId); }
}
