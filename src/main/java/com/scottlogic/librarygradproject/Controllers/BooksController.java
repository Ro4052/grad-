package com.scottlogic.librarygradproject.Controllers;

import com.scottlogic.librarygradproject.Entities.Book;
import com.scottlogic.librarygradproject.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BooksController {

    private final BookService bookService;

    @Autowired
    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public List<Book> getAll() {
        return bookService.findAll();
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.GET)
    public Book get(@PathVariable long id) {
        return bookService.findOne(id);
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable long id) {
        bookService.delete(id);
    }

    @RequestMapping(value = "/books", method = RequestMethod.DELETE)
    public void delete(@RequestBody() List<Long> ids) {
        bookService.removeMultiple(ids);
    }

    @RequestMapping(value = "/books", method = RequestMethod.POST)
    public void post(@RequestBody() Book book) {
        bookService.save(book);
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.PUT)
    public void put(@RequestBody() Book book) {
        bookService.put(book);
    }
}
