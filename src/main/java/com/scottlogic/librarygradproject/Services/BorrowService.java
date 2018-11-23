package com.scottlogic.librarygradproject.Services;

import com.scottlogic.librarygradproject.Entities.Borrow;
import com.scottlogic.librarygradproject.Exceptions.BookAlreadyBorrowedException;
import com.scottlogic.librarygradproject.Exceptions.BorrowNotFoundException;
import com.scottlogic.librarygradproject.Repositories.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
        if (this.isBorrowed(bookId)) throw new BookAlreadyBorrowedException(bookId);
        String userId = userService.loggedIn(authentication).getUserId();
        LocalDate borrowDate = LocalDate.now();
        LocalDate returnDate = LocalDate.now().plusDays(7);
        Borrow loan = Borrow.builder().bookId(bookId).userId(userId).isActive(true).borrowDate(borrowDate).returnDate(returnDate).build();
        borrowRepository.save(loan);
    }

    public List<Borrow> findAll() {
        return borrowRepository.findAll();
    }

    public Borrow findOne(long borrowId) {
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

    public boolean isBorrowed(long bookId) {
        bookService.findOne(bookId);
        return borrowRepository.isBookBorrowed(bookId);
    }

    @Transactional
    @Modifying
    public List<Long> updateBorrowed(LocalDate currentDate) {
        List<Long> bookIds = new ArrayList<>();
        try (Stream<Borrow> borrows = borrowRepository.findActiveBorrows(currentDate)) {
            borrows.forEach(borrow -> {
                bookIds.add(borrow.getBookId());
                borrow.setActive(false);
            });
        }
        return bookIds;
    }
}
