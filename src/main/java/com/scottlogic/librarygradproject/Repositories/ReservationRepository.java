package com.scottlogic.librarygradproject.Repositories;

import com.scottlogic.librarygradproject.Entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query(value = "SELECT COUNT(*) FROM public.reservation WHERE book_id = :bookId", nativeQuery = true)
    long findLatestQueue(@Param("bookId") long bookId);

}
