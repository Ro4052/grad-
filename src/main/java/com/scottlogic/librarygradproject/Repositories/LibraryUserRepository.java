package com.scottlogic.librarygradproject.Repositories;

import com.scottlogic.librarygradproject.Entities.LibraryUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryUserRepository extends JpaRepository<LibraryUser, String> {}
