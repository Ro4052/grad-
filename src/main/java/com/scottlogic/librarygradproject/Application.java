package com.scottlogic.librarygradproject;

import com.scottlogic.librarygradproject.Helpers.BorrowHelper;
import com.scottlogic.librarygradproject.Helpers.UserHelper;
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
    public UserHelper getUserHelper() {
        return new UserHelper();
    }

    @Bean
    @Autowired
    public UserService getUserService(UserHelper userHelper, LibraryUserRepository userRepo,
                                      ReservationRepository reservationRepo, BorrowRepository borrowRepo) {
        return new UserService(userHelper, userRepo, reservationRepo, borrowRepo);
    }

    @Autowired
    BookRepository bookRepo;

    @Bean
    @Autowired
    public BookService getBookService(BookRepository bookRepo, BorrowRepository borrowRepo,
                                      ReservationRepository reservationRepo) {
        return new BookService(bookRepo, borrowRepo, reservationRepo);
    }

    @Autowired
    ReservationRepository reservationRepo;

    @Bean
    @Autowired
    public ReservationService getReservationService(ReservationRepository reservationRepo, BookRepository bookRepo,
                                                    UserHelper userHelper, LibraryUserRepository userRepo,
                                                    BorrowHelper borrowHelper, BorrowRepository borrowRepo) {
        return new ReservationService(reservationRepo, bookRepo, userHelper, userRepo, borrowHelper, borrowRepo);
    }

    @Autowired
    BorrowRepository borrowRepo;

    @Bean
    @Autowired
    public BorrowHelper getBorrowHelper(BookRepository bookRepo, BorrowRepository borrowRepo,
                                        ReservationRepository reservationRepo) {
        return new BorrowHelper(bookRepo, borrowRepo, reservationRepo);
    }

    @Bean
    @Autowired
    public BorrowService getBorrowService(BorrowHelper borrowHelper, BorrowRepository borrowRepo,
                                          BookRepository bookRepo, ReservationRepository reservationRepo) {
        return new BorrowService(borrowHelper, borrowRepo, bookRepo, reservationRepo); }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
