package com.scottlogic.librarygradproject.cucumber.steps;


import com.scottlogic.librarygradproject.*;
import cucumber.api.PendingException;
import cucumber.api.java8.En;
import org.junit.Test;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

import static org.junit.Assert.*;



public class Stepdefs implements En {
    private boolean run = false;
    BookRepository bookRepo;
    FilledBookRepository filledRepo;
    BooksController controller;
    Book newBook;
    int repoLength;
    public Stepdefs() {

        Given("a book repository exists", () -> {
            bookRepo = new BookRepository();
            controller = new BooksController(bookRepo);
        });

        When("An add book request is received with correct book details", () -> {
            newBook = new Book("0123456789", "Correct Book", "Correct Author", "1999");
            controller.post(newBook);
        });

        Then("the book should enter the database", () ->{
           assertEquals(bookRepo.getAll().size(), 1);
        });
        When("^An add book request is received with incorrect ISBN$", () -> {
            newBook = new Book("012345678", "Correct Book", "Correct Author", "1999");
            try {
                controller.post(newBook);
            }
            catch (IncorrectBookFormatException e) {}
        });
        Then("^the book should not enter the database$", () -> {
            assertEquals(bookRepo.getAll().size(), 0);
        });
        When("^An add book request is received without author$", () -> {
            newBook = new Book("0123456789", "Correct Book", "", "1999");
            try {
                controller.post(newBook);
            }
            catch (IncorrectBookFormatException e) {}
        });
        When("^An add book request is received without title$", () -> {
            newBook = new Book("0123456789", "", "Correct Author", "1999");
            try {
                controller.post(newBook);
            }
            catch (IncorrectBookFormatException e) {}
        });
        When("^An add book request is received with author too long$", () -> {
            String longAuthor = StringUtils.repeat("A", 201);
            newBook = new Book("0123456789", "Correct Book", longAuthor, "1999");
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
            newBook = new Book("0123456789", "Correct Title", "Correct Author", "19991");
            try {
                controller.post(newBook);
            }
            catch (IncorrectBookFormatException e) {}
        });
        When("^An add book request is received with leading and trailing whitespace in fields otherwise acceptable$", () -> {
            newBook = new Book("  0123456789123  ", " Correct Title   ", " Correct Author   ", " 1999  ");
                controller.post(newBook);
        });
        When("^An add book request is received with leading and trailing whitespace in fields and unacceptable$", () -> {
            newBook = new Book("  012345678911  ", " Correct Title   ", " Correct Author   ", " 1999  ");
            try {
                controller.post(newBook);
            }
            catch (IncorrectBookFormatException e) {}
        });
        Given("^a filled book repository exists$", () -> {
            filledRepo = new FilledBookRepository();
            controller = new BooksController(filledRepo);
        });
        When("^An edit book request is received with correct book details$", () -> {
            newBook = new Book("0123456789", "Correct Book", "Correct Author", "1999");
            controller.put(0, newBook);
        });
        Then("^the modified book should replace the original$", () -> {
            assertEquals(filledRepo.get(0), newBook);
        });
        When("^An edit book request is received with incorrect ISBN$", () -> {
            newBook = new Book("012345678", "Correct Book", "Correct Author", "1999");
            try {
                controller.put(0, newBook);
            }
            catch (IncorrectBookFormatException e) {}
        });
        Then("^the book should not be edited$", () -> {
            assertNotEquals(filledRepo.get(0), newBook);
        });
        When("^An edit book request is received without author$", () -> {
            newBook = new Book("0123456789", "Correct Book","", "1999");
            try {
                controller.put(0, newBook);
            }
            catch (IncorrectBookFormatException e) {}
        });
        When("^An edit book request is received without title$", () -> {
            newBook = new Book("0123456789", "","Correct Title", "1999");
            try {
                controller.put(0, newBook);
            }
            catch (IncorrectBookFormatException e) {}
        });
        When("^An edit book request is received with author too long$", () -> {
            String longAuthor = StringUtils.repeat("A", 201);
            newBook = new Book("0123456789", "Correct Book", longAuthor, "1999");
            try {
                controller.put(0, newBook);
            }
            catch (IncorrectBookFormatException e) {}
        });
        When("^An edit book request is received with title too long$", () -> {
            String longTitle = StringUtils.repeat("A", 201);
            newBook = new Book("0123456789", longTitle, "Correct Author", "1999");
            try {
                controller.put(0, newBook);
            }
            catch (IncorrectBookFormatException e) {}
        });
        When("^An edit book request is received with incorrect date format$", () -> {
            newBook = new Book("0123456789", "Correct Book", "Correct Author", "19998");
            try {
                controller.put(0, newBook);
            }
            catch (IncorrectBookFormatException e) {}
        });
        When("^An edit book request is received with leading and trailing whitespace in fields otherwise acceptable$", () -> {
            newBook = new Book("   0123456789    ", "  Correct Book  ", "  Correct Author  ", "  1999      ");
            Book trimBook = new Book("0123456789", "Correct Book", "Correct Author", "1999");
            controller.put(0, newBook);
            newBook = trimBook;
        });
        When("^An edit book request is received with leading and trailing whitespace in fields and unacceptable$", () -> {
            newBook = new Book("   012345679    ", "  Correct Book  ", "  Correct Author  ", "  19939      ");
            Book trimBook = new Book("012345679", "Correct Book", "Correct Author", "19939");
            try {
                controller.put(0, newBook);
            }
            catch (IncorrectBookFormatException e) {}
            newBook = trimBook;
        });
        When("^An edit book request is received without isbn$", () -> {
            newBook = new Book("", "Correct Book", "Correct Author", "1999");
            controller.put(0, newBook);
        });
        When("^An edit book request is received without publish date$", () -> {
            newBook = new Book("0123456789", "Correct Book", "Correct Author", "");
            controller.put(0, newBook);
        });
        Given("^A book exists without isbn$", () -> {
            newBook = new Book("", "Correct Book", "Correct Author", "1999");
            controller.put(0, newBook);
        });
        When("^The book is edited to have isbn$", () -> {
            newBook = new Book("0123456789", "Correct Book", "Correct Author", "1999");
            controller.put(0, newBook);
        });
        Given("^A book exists without publish date$", () -> {
            newBook = new Book("0123456789", "Correct Book", "Correct Author", "");
            controller.put(0, newBook);
        });
        When("^The book is edited to have publish date$", () -> {
            newBook = new Book("0123456789", "Correct Book", "Correct Author", "1999");
            controller.put(0, newBook);
        });
        When("^An add book request is received without isbn$", () -> {
            newBook = new Book("", "Correct Book", "Correct Author", "1999");
            controller.post(newBook);
        });
        When("^An add book request is received without publish date$", () -> {
            newBook = new Book("0123456789", "Correct Book", "Correct Author", "");
            controller.post(newBook);
        });
        When("^A delete request is received for a book that exists$", () -> {
            repoLength = controller.getAll().size();
            controller.delete(0);
        });
        Then("^That book is deleted$", () -> {
            try {
                controller.get(0);
            }
            catch (BookNotFoundException e) {}
            assertEquals(repoLength-1,controller.getAll().size());
        });
        When("^A delete request is received for a book that does not exist$", () -> {
            repoLength = controller.getAll().size();
            try {
                controller.delete(7);
            }
            catch (BookNotFoundException e) {}
        });
        When("^A delete request is received for multiple books that exist$", () -> {
            repoLength = controller.getAll().size();
            controller.delete(Arrays.asList(0,1));
        });
        Then("^those books are deleted$", () -> {
            try {controller.get(0);} catch (BookNotFoundException e) {}
            try {controller.get(1);} catch (BookNotFoundException e) {}
            assertEquals(repoLength-2,controller.getAll().size());
        });
        When("^A delete request is received for multiple books, only some of which exist$", () -> {
            repoLength = controller.getAll().size();
            try {controller.delete(Arrays.asList(0,8));} catch  (BookNotFoundException e) {}
        });
        Then("^no books should be deleted$", () -> {
            assertEquals(repoLength, controller.getAll().size());
        });
        Then("^existing books should be deleted$", () -> {
            try {
                controller.get(0);
            }
            catch (BookNotFoundException e) {}
            assertEquals(repoLength-1,controller.getAll().size());
        });
        When("^A delete request is received for multiple books, none of which exist$", () -> {
            repoLength = controller.getAll().size();
            try {controller.delete(Arrays.asList(9,8));} catch  (BookNotFoundException e) {}
        });


    }

}