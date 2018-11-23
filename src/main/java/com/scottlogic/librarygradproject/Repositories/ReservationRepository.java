package com.scottlogic.librarygradproject.Repositories;

import com.scottlogic.librarygradproject.Entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query(value = "SELECT COUNT(*) FROM public.reservation WHERE book_id = :bookId", nativeQuery = true)
    long findLatestQueue(@Param("bookId") long bookId);

    @Query(value = "SELECT COALESCE(( SELECT MIN(queue_position) FROM public.reservation WHERE book_id = :bookId), 0)", nativeQuery = true)
    int findNextInQueue(@Param("bookId") long bookId);

    List<Reservation> findAllByBookId(long bookId);
}
