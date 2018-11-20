package com.scottlogic.librarygradproject.cucumber.steps;


import com.scottlogic.librarygradproject.Controllers.BooksController;
import com.scottlogic.librarygradproject.Controllers.ReservationsController;
import com.scottlogic.librarygradproject.Entities.Book;
import com.scottlogic.librarygradproject.Exceptions.BookNotFoundException;
import com.scottlogic.librarygradproject.Exceptions.IncorrectBookFormatException;
import com.scottlogic.librarygradproject.Services.BookService;
import com.scottlogic.librarygradproject.Services.ReservationService;
import cucumber.api.PendingException;
import cucumber.api.java8.En;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@DataJpaTest
public class Stepdefs implements En {
    private boolean run = false;

    @Autowired
    BookService bookService;
    ReservationService resService;
    BooksController controller;
    ReservationsController rescontroller;
    Book newBook;
    int repoLength;
    long id;
    long id2;
    long id3;
    Book existingBook;
    Book existingBook2;
    Book existingBook3;
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
            existingBook = new Book("1148712399", "Existing Book", "Existing Author", "1966");
            existingBook2 = new Book("1148312399", "Existing Book", "Existing Author", "1966");
            existingBook3 = new Book("1148712699", "Existing Book", "Existing Author", "1966");
            controller.post(existingBook);
            controller.post(existingBook2);
            controller.post(existingBook3);
            id = controller.getAll().get(0).getId();
            id2 = controller.getAll().get(1).getId();
            id3 = controller.getAll().get(2).getId();
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
        Then("^the book should not be edited$", () -> {
            assertEquals(bookService.findOne(id), existingBook);
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
        When("^A delete request is received for a book that exists$", () -> {
            repoLength = controller.getAll().size();
            controller.delete(id);
        });
        Then("^That book is deleted$", () -> {
            try {
                bookService.findOne(id);
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
            List<Long> ids = new ArrayList<>();
            ids.add(id);
            ids.add(id2);
            controller.delete(ids);
        });
        Then("^those books are deleted$", () -> {
            try {bookService.findOne(id);} catch (BookNotFoundException e) {}
            try {bookService.findOne(id2);} catch (BookNotFoundException e) {}
            assertEquals(repoLength-2,controller.getAll().size());
        });
        When("^A delete request is received for multiple books, only some of which exist$", () -> {
            repoLength = controller.getAll().size();
            List<Long> ids = new ArrayList<>();
            ids.add(id);
            long ne = -5;
            ids.add(ne);
            try {controller.delete(ids);} catch  (BookNotFoundException e) {}
        });
        Then("^no books should be deleted$", () -> {
            assertEquals(repoLength, controller.getAll().size());
        });
        Then("^existing books should be deleted$", () -> {
            try {
                bookService.findOne(id);
            }
            catch (BookNotFoundException e) {}
            assertEquals(repoLength-1,controller.getAll().size());
        });
        When("^A delete request is received for multiple books, none of which exist$", () -> {
            repoLength = controller.getAll().size();
            long ne = -1;
            long ne2 = -2;
            try {controller.delete(Arrays.asList(ne,ne2));} catch  (BookNotFoundException e) {}
        });
        Given("^an empty reservation table exists$", () -> {
            resService.deleteAll();
            rescontroller = new ReservationsController(resService);
        });
        When("^a reservation is made on a book by an authorised user$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        Then("^that reservation is added to the database$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        When("^a reservation is made on a book by an unauthorised user$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        Then("^that reservation is not added to the database$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        When("^an availability check is made on a book$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        Then("^the status of the book should be displayed as available$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        Then("^the status of the book should be reserved with a queue of (\\d+)$", (Integer arg0) -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
    }
}
