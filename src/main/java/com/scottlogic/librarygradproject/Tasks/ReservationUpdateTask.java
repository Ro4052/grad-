package com.scottlogic.librarygradproject.Tasks;

import com.scottlogic.librarygradproject.Services.ReservationService;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ReservationUpdateTask {

    private final ReservationService reservationService;

    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler();
    }

    public ReservationUpdateTask(ReservationService reservationService) { this.reservationService = reservationService; }

    @Scheduled(cron = "0 0 0 * * *")
    public void checkExpiredReservations() {
        reservationService.updateReservations(LocalDate.now());
    }
}
