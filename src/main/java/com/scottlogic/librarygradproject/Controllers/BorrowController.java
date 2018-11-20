package com.scottlogic.librarygradproject.Controllers;

import com.scottlogic.librarygradproject.Services.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BorrowController {
    private final BorrowService borrowService;

    @Autowired
    BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    @RequestMapping(value = "/borrow/{bookId}", method = RequestMethod.POST)
    public void post(@PathVariable long bookId, OAuth2Authentication authentication) { borrowService.borrow(bookId, authentication); }

}
