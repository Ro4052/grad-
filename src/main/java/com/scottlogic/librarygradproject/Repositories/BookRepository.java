package com.scottlogic.librarygradproject.Repositories;

import com.scottlogic.librarygradproject.Entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM public.reservation WHERE book_id NOT IN (SELECT id FROM public.book)", nativeQuery = true)
    void clearDeletedBookReservations();
}

