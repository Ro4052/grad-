package com.scottlogic.librarygradproject.Controllers;

import com.scottlogic.librarygradproject.Entities.Borrow;
import com.scottlogic.librarygradproject.Services.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BorrowController {
    private final BorrowService borrowService;

    @Autowired
    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    @RequestMapping(value = "/borrow/{bookId}", method = RequestMethod.POST)
    public void post(@PathVariable long bookId, OAuth2Authentication authentication) {
        borrowService.borrow(bookId, authentication);
    }

    @RequestMapping(value = "/borrow", method = RequestMethod.GET)
    public List<Borrow> getAll() { return borrowService.findAll(); }

    @RequestMapping(value = "/borrow/{borrowId}", method = RequestMethod.GET)
    public Borrow get(@PathVariable long borrowId) { return borrowService.findOne(borrowId); }

    @RequestMapping(value = "/borrow/{borrowId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable long borrowId) {borrowService.delete(borrowId); }

    @RequestMapping(value = "/borrow/check/{bookId}", method = RequestMethod.GET)
    public boolean check(@PathVariable long bookId) { return borrowService.isBorrowed(bookId); }
}
