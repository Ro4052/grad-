package com.scottlogic.librarygradproject.Repositories;

import com.scottlogic.librarygradproject.Entities.Reservation;
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
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query(value = "SELECT COUNT(*) FROM public.reservation WHERE book_id = :bookId", nativeQuery = true)
    long findLatestQueue(@Param("bookId") long bookId);

    @Query(value = "SELECT EXISTS(SELECT * FROM public.reservation WHERE collect_by IS NOT null AND book_id = :bookId)", nativeQuery = true)
    boolean isBookAwaitingCollection(@Param("bookId") long bookId);

    @Query(value = "SELECT * FROM public.reservation WHERE collect_by < :date", nativeQuery = true)
    Stream<Reservation> findOverdueReservations(@Param("date") LocalDate date);

    List<Reservation> findAllByBookIdOrderByQueuePositionAsc(long bookId);

    List<Reservation> findAllByUserId(String userId);

    Boolean existsByUserIdAndBookId(String userId, long bookId);

    Reservation findOneByBookIdAndQueuePosition(long bookId, long queuePosition);

    @Transactional
    void deleteByBookId(long bookId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM public.reservation WHERE book_id IN :bookIds", nativeQuery = true)
    void deleteByMultipleIds(@Param("bookIds") List<Long> bookIds);
}
