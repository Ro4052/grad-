package com.scottlogic.librarygradproject;

import com.scottlogic.librarygradproject.Repositories.BookRepository;
import com.scottlogic.librarygradproject.Repositories.BorrowRepository;
import com.scottlogic.librarygradproject.Repositories.LibraryUserRepository;
import com.scottlogic.librarygradproject.Repositories.ReservationRepository;
import com.scottlogic.librarygradproject.Services.BookService;
import com.scottlogic.librarygradproject.Services.BorrowService;
import com.scottlogic.librarygradproject.Services.ReservationService;
import com.scottlogic.librarygradproject.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Application {

    @Autowired
    LibraryUserRepository userRepo;

    @Bean
    @Autowired
    public UserService getUserService(LibraryUserRepository userRepo, ReservationRepository reservationRepo,
                                      BorrowRepository borrowRepo) {
        return new UserService(userRepo, reservationRepo, borrowRepo);
    }

    @Autowired
    BookRepository bookRepo;

    @Bean
    @Autowired
    public BookService getBookService(BookRepository bookRepo) {
        return new BookService(bookRepo);
    }

    @Autowired
    ReservationRepository reservationRepo;

    @Bean
    @Autowired
    public ReservationService getReservationService(ReservationRepository reservationRepo, BookRepository bookRepo,
                                                    UserService userService, BorrowService borrowService) {
        return new ReservationService(reservationRepo, bookRepo, userService, borrowService);
    }

    @Autowired
    BorrowRepository borrowRepo;

    @Bean
    @Autowired
    public BorrowService getBorrowService(BorrowRepository borrowRepo, BookRepository bookRepo,
                                          ReservationRepository reservationRepo) {
        return new BorrowService(borrowRepo, bookRepo, reservationRepo); }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
