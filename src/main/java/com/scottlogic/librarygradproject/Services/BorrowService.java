package com.scottlogic.librarygradproject.Services;

import com.scottlogic.librarygradproject.Entities.Borrow;
import com.scottlogic.librarygradproject.Repositories.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.Date;

public class BorrowService {

    private final BorrowRepository borrowRepository;
    private final UserService userService;

    @Autowired
    public BorrowService(BorrowRepository borrowRepository, UserService userService) {
        this.borrowRepository = borrowRepository;
        this.userService = userService;
    }

    public void borrow(long bookId, OAuth2Authentication authentication) {
        String userId = userService.loggedIn(authentication).getUserId();
        Date date = new Date();
        Borrow loan = Borrow.builder().bookId(bookId).userId(userId).isActive(true).borrowDate(date).build();
        borrowRepository.save(loan);
    }
}
