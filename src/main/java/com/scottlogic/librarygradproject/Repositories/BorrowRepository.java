package com.scottlogic.librarygradproject.Repositories;

import com.scottlogic.librarygradproject.Entities.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.stream.Stream;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Long> {

    @Query(value = "SELECT COALESCE ((SELECT is_active FROM public.borrow WHERE book_id = :bookId AND is_active = true), false)", nativeQuery = true)
    boolean isBookBorrowed(@Param("bookId") long bookId);

    @Query(value = "SELECT * FROM public.borrow WHERE return_date < :date AND is_active = true", nativeQuery = true)
    Stream<Borrow> findOverdueBorrows(@Param("date") LocalDate date);
}
