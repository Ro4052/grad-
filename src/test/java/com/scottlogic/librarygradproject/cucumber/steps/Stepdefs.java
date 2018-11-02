package com.scottlogic.librarygradproject.cucumber.steps;


import com.scottlogic.librarygradproject.Book;
import com.scottlogic.librarygradproject.BookRepository;
import com.scottlogic.librarygradproject.BooksController;
import com.scottlogic.librarygradproject.IncorrectBookFormatException;
import cucumber.api.PendingException;
import cucumber.api.java8.En;
import org.junit.Test;
import org.apache.commons.lang3.StringUtils;

import static org.junit.Assert.*;



public class Stepdefs implements En {
    private boolean run = false;
    BookRepository bookRepo;
    BooksController controller;
    public Stepdefs() {

        Given("test is run", () -> this.run = true);

        Then("test should succeed", () ->  assertTrue(run));

        Given("a book repository exists", () -> {
            bookRepo = new BookRepository();
            controller = new BooksController(bookRepo);
        });

        When("An add book request is received with correct book details", () -> {
            Book newBook = new Book("0123456789", "Correct Book", "Correct Author", "1999");
            controller.post(newBook);
        });

        Then("the book should enter the database", () ->{
           assertEquals(bookRepo.getAll().size(), 1);
        });
        When("^An add book request is received with incorrect ISBN$", () -> {
            Book newBook = new Book("012345678", "Correct Book", "Correct Author", "1999");
            try {
                controller.post(newBook);
            }
            catch (IncorrectBookFormatException e) {}
        });
        Then("^the book should not enter the database$", () -> {
            assertEquals(bookRepo.getAll().size(), 0);
        });
        When("^An add book request is received without author$", () -> {
            Book newBook = new Book("0123456789", "Correct Book", "", "1999");
            try {
                controller.post(newBook);
            }
            catch (IncorrectBookFormatException e) {}
        });
        When("^An add book request is received without title$", () -> {
            Book newBook = new Book("0123456789", "", "Correct Author", "1999");
            try {
                controller.post(newBook);
            }
            catch (IncorrectBookFormatException e) {}
        });
        When("^An add book request is received with author too long$", () -> {
            String longAuthor = StringUtils.repeat("A", 201);
            Book newBook = new Book("0123456789", "Correct Book", longAuthor, "1999");
            try {
                controller.post(newBook);
            }
            catch (IncorrectBookFormatException e) {}
        });
        When("^An add book request is received with title too long$", () -> {
            String longTitle = StringUtils.repeat("A", 201);
            Book newBook = new Book("0123456789", longTitle, "Correct Author", "1999");
            try {
                controller.post(newBook);
            }
            catch (IncorrectBookFormatException e) {}
        });
        When("^An add book request is received with incorrect date format$", () -> {
            Book newBook = new Book("0123456789", "Correct Title", "Correct Author", "19991");
            try {
                controller.post(newBook);
            }
            catch (IncorrectBookFormatException e) {}
        });
        When("^An add book request is received with leading and trailing whitespace in fields otherwise acceptable$", () -> {
            Book newBook = new Book("  0123456789123  ", " Correct Title   ", " Correct Author   ", " 1999  ");
                controller.post(newBook);
        });
        When("^An add book request is received with leading and trailing whitespace in fields and unacceptable$", () -> {
            Book newBook = new Book("  012345678911  ", " Correct Title   ", " Correct Author   ", " 1999  ");
            try {
                controller.post(newBook);
            }
            catch (IncorrectBookFormatException e) {}
        });

    }

}