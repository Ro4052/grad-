package com.scottlogic.librarygradproject.Repositories;

import com.scottlogic.librarygradproject.Entities.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Long> {

    @Query(value = "SELECT COALESCE ((SELECT is_active FROM public.borrow WHERE book_id = :bookId AND is_active = true), false)", nativeQuery = true)
    boolean isBookBorrowed(@Param("bookId") long bookId);

    @Query(value = "SELECT * FROM public.borrow WHERE return_date < :date AND is_active = true", nativeQuery = true)
    Stream<Borrow> findOverdueBorrows(@Param("date") LocalDate date);

    List<Borrow> findAllByUserId(String userId);

    Boolean existsByUserIdAndBookIdAndIsActive(String userId, long bookId, boolean isActive);

    @Transactional
    @Modifying
    @Query(value = "UPDATE public.borrow SET is_active = false WHERE book_id = :bookId", nativeQuery = true)
    void deactivateByBookId(@Param("bookId") long bookId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE public.borrow SET is_active = false", nativeQuery = true)
    void deactivateAll();

    @Transactional
    @Modifying
    @Query(value = "UPDATE public.borrow SET is_active = false WHERE book_id IN :bookIds", nativeQuery = true)
    void deactivateByMultipleIds(@Param("bookIds") List<Long> bookIds);
}
