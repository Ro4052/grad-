package com.scottlogic.librarygradproject.cucumber.steps;


import com.scottlogic.librarygradproject.Controllers.BooksController;
import com.scottlogic.librarygradproject.Entities.Book;
import com.scottlogic.librarygradproject.Exceptions.IncorrectBookFormatException;
import com.scottlogic.librarygradproject.Services.BookService;
import cucumber.api.java8.En;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.Assert.*;

@DataJpaTest
public class Stepdefs implements En {
    private boolean run = false;

    @Autowired
    BookService bookService;

    BooksController controller;
    Book newBook;
    long id;
    public Stepdefs() {

        Given("a book repository exists", () -> {
            bookService.deleteAll();
            controller = new BooksController(bookService);
        });

        When("An add book request is received with correct book details", () -> {
            newBook = new Book("0123456789", "Correct Book", "Correct Author", "1999");
            controller.post(newBook);
        });

        Then("the book should enter the database", () ->{
           assertEquals(bookService.findAll().size(), 1);
        });
        When("^An add book request is received with incorrect ISBN$", () -> {
            newBook = new Book("012345678", "Correct Book", "Correct Author", "1999");
            try {
                controller.post(newBook);
            }
            catch (IncorrectBookFormatException e) {}
        });
        Then("^the book should not enter the database$", () -> {
            assertEquals(bookService.findAll().size(), 0);
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
            bookService.deleteAll();
            controller = new BooksController(bookService);
            Book existingBook = new Book("1148712399", "Existing Book", "Existing Author", "1966");
            controller.post(existingBook);
            id = controller.getAll().get(0).getId();
        });
        When("^An edit book request is received with correct book details$", () -> {
            newBook = new Book("0123456789", "Correct Book", "Correct Author", "1999");
            newBook.setId(id);
            controller.put(newBook);
            System.out.println(controller.getAll().get(0).getId());
        });
        Then("^the modified book should replace the original$", () -> {
            assertEquals(bookService.findOne(id), newBook);
        });
        When("^An edit book request is received with incorrect ISBN$", () -> {
            newBook = new Book("012345678", "Correct Book", "Correct Author", "1999");
            newBook.setId(id);
            try {
                controller.put(newBook);
            }
            catch (IncorrectBookFormatException e) {}
        });
        Then("^the database should not be modified$", () -> {
            assertNotEquals(bookService.findOne(id), newBook);
        });
        When("^An edit book request is received without author$", () -> {
            newBook = new Book("0123456789", "Correct Book","", "1999");
            newBook.setId(id);
            try {
                controller.put(newBook);
            }
            catch (IncorrectBookFormatException e) {}
        });
        When("^An edit book request is received without title$", () -> {
            newBook = new Book("0123456789", "","Correct Title", "1999");
            newBook.setId(id);
            try {
                controller.put(newBook);
            }
            catch (IncorrectBookFormatException e) {}
        });
        When("^An edit book request is received with author too long$", () -> {
            String longAuthor = StringUtils.repeat("A", 201);
            newBook = new Book("0123456789", "Correct Book", longAuthor, "1999");
            newBook.setId(id);
            try {
                controller.put(newBook);
            }
            catch (IncorrectBookFormatException e) {}
        });
        When("^An edit book request is received with title too long$", () -> {
            String longTitle = StringUtils.repeat("A", 201);
            newBook = new Book("0123456789", longTitle, "Correct Author", "1999");
            newBook.setId(id);
            try {
                controller.put(newBook);
            }
            catch (IncorrectBookFormatException e) {}
        });
        When("^An edit book request is received with incorrect date format$", () -> {
            newBook = new Book("0123456789", "Correct Book", "Correct Author", "19998");
            newBook.setId(id);
            try {
                controller.put(newBook);
            }
            catch (IncorrectBookFormatException e) {}
        });
        When("^An edit book request is received with leading and trailing whitespace in fields otherwise acceptable$", () -> {
            newBook = new Book("   0123456789    ", "  Correct Book  ", "  Correct Author  ", "  1999      ");
            newBook.setId(id);
            Book trimBook = new Book("0123456789", "Correct Book", "Correct Author", "1999");
            controller.put(newBook);
            newBook = trimBook;
        });
        When("^An edit book request is received with leading and trailing whitespace in fields and unacceptable$", () -> {
            newBook = new Book("   012345679    ", "  Correct Book  ", "  Correct Author  ", "  19939      ");
            newBook.setId(id);
            Book trimBook = new Book("012345679", "Correct Book", "Correct Author", "19939");
            try {
                controller.put(newBook);
            }
            catch (IncorrectBookFormatException e) {}
            newBook = trimBook;
        });
        When("^An edit book request is received without isbn$", () -> {
            newBook = new Book("", "Correct Book", "Correct Author", "1999");
            newBook.setId(id);
            controller.put(newBook);
        });
        When("^An edit book request is received without publish date$", () -> {
            newBook = new Book("0123456789", "Correct Book", "Correct Author", "");
            newBook.setId(id);
            controller.put(newBook);
        });
        Given("^A book exists without isbn$", () -> {
            newBook = new Book("", "Correct Book", "Correct Author", "1999");
            newBook.setId(id);
            controller.put(newBook);
        });
        When("^The book is edited to have isbn$", () -> {
            newBook = new Book("0123456789", "Correct Book", "Correct Author", "1999");
            newBook.setId(id);
            controller.put(newBook);
        });
        Given("^A book exists without publish date$", () -> {
            newBook = new Book("0123456789", "Correct Book", "Correct Author", "");
            newBook.setId(id);
            controller.put(newBook);
        });
        When("^The book is edited to have publish date$", () -> {
            newBook = new Book("0123456789", "Correct Book", "Correct Author", "1999");
            newBook.setId(id);
            controller.put(newBook);
        });
        When("^An add book request is received without isbn$", () -> {
            newBook = new Book("", "Correct Book", "Correct Author", "1999");
            controller.post(newBook);
        });
        When("^An add book request is received without publish date$", () -> {
            newBook = new Book("0123456789", "Correct Book", "Correct Author", "");
            controller.post(newBook);
        });
    }
}
