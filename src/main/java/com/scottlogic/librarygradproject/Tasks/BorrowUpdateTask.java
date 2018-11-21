package com.scottlogic.librarygradproject.Tasks;

import java.time.LocalDate;
import com.scottlogic.librarygradproject.Services.BorrowService;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Component;

@Component
public class BorrowUpdateTask {

    private final BorrowService borrowService;

    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler();
    }

    public BorrowUpdateTask(BorrowService borrowService) {

        this.borrowService = borrowService;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void reportCurrentTime() {
        borrowService.updateBorrowed(LocalDate.now());
    }
}