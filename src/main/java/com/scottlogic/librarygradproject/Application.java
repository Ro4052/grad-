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

@SpringBootApplication
public class Application {

    @Autowired
    LibraryUserRepository userRepo;

    @Bean
    @Autowired
    public UserService getUserService(LibraryUserRepository userRepo) {
        return new UserService(userRepo);
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
    public ReservationService getReservationService(ReservationRepository reservationRepo, BookService bookService, UserService userService) {
        return new ReservationService(reservationRepo, bookService, userService);
    }

    @Autowired
    BorrowRepository borrowRepository;

    @Bean
    @Autowired
    public BorrowService getBorrowService(BorrowRepository borrowRepository, UserService userService) { return new BorrowService(borrowRepository, userService); }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
