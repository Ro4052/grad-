package com.scottlogic.librarygradproject.Services;

import com.scottlogic.librarygradproject.Entities.Borrow;
import com.scottlogic.librarygradproject.Exceptions.BorrowNotFoundException;
import com.scottlogic.librarygradproject.Repositories.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class BorrowService {

    private final BorrowRepository borrowRepository;
    private final UserService userService;
    private final BookService bookService;

    @Autowired
    public BorrowService(BorrowRepository borrowRepository, UserService userService, BookService bookService) {
        this.borrowRepository = borrowRepository;
        this.userService = userService;
        this.bookService = bookService;
    }

    public void borrow(long bookId, OAuth2Authentication authentication) {
        bookService.findOne(bookId);
        String userId = userService.loggedIn(authentication).getUserId();
        Date date = new Date();
        Borrow loan = Borrow.builder().bookId(bookId).userId(userId).isActive(true).borrowDate(date).build();
        borrowRepository.save(loan);
    }

    public List<Borrow> findAll() {
        return borrowRepository.findAll();
    }

    public Borrow get(long borrowId) {
        Optional<Borrow> borrowToGet = borrowRepository.findById(borrowId);
        return borrowToGet.orElseThrow(() -> new BorrowNotFoundException(borrowId));
    }

    public void delete(long borrowId) {
        try {
            borrowRepository.deleteById(borrowId);
        } catch (EmptyResultDataAccessException e) {
            throw new BorrowNotFoundException(borrowId);
        }
    }

    public boolean check(long bookId) {
        bookService.findOne(bookId);
        return borrowRepository.isBookBorrowed(bookId);
    }
}
