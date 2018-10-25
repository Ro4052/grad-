package com.scottlogic.librarygradproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BooksController {

    private BookRepository bookRepo;

    @Autowired
    public BooksController(BookRepository bookRepository){
        bookRepo = bookRepository;
    }

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public List<Book> getAll() {
        return bookRepo.getAll();
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.GET)
    public Book get(@PathVariable int id) {
        return bookRepo.get(id);
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) {
        bookRepo.remove(id);
    }

    @RequestMapping(value = "/books", method = RequestMethod.POST)
    public void post(@RequestBody() Book book) {
        bookRepo.add(book);
    }
}
