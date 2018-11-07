package com.scottlogic.librarygradproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    @Autowired
    BookRepository bookRepository;

    @Bean
    @Autowired
    public BookService getBookService(BookRepository bookRepository) {
        return new BookService(bookRepository);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}