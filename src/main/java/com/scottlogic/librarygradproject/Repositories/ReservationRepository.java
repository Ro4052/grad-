package com.scottlogic.librarygradproject.Repositories;

import com.scottlogic.librarygradproject.Entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query(value = "SELECT COUNT(*) FROM public.reservation WHERE book_id = :bookId", nativeQuery = true)
    long findLatestQueue(@Param("bookId") long bookId);

    @Query(value = "SELECT * FROM public.reservation WHERE collect_by < :date", nativeQuery = true)
    Stream<Reservation> findOverdueReservations(@Param("date") LocalDate date);

    List<Reservation> findAllByBookIdOrderByQueuePositionAsc(long bookId);

    List<Reservation> findAllByUserId(String userId);

    Boolean existsByUserIdAndBookId(String userId, long bookId);
}
