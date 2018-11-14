package com.scottlogic.librarygradproject.Repositories;

import com.scottlogic.librarygradproject.Entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface BookRepository extends JpaRepository<Book, Long> {
}

