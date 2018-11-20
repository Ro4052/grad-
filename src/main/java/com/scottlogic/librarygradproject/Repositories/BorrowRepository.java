package com.scottlogic.librarygradproject.Repositories;

import com.scottlogic.librarygradproject.Entities.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Long> {
}
