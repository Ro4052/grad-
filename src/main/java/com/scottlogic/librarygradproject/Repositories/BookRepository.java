package com.scottlogic.librarygradproject.Repositories;

import com.scottlogic.librarygradproject.Entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO book (isbn, title, author, publish_date) VALUES(:#{#newBook.isbn}, " +
            ":#{#newBook.title}, :#{#newBook.author}, :#{#newBook.publishDate})", nativeQuery = true)
    public void insert(@Param("newBook") Book newBook);

}
