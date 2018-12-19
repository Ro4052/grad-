package com.scottlogic.librarygradproject.Entities;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@Entity
public class Reservation {
    @Id
    @SequenceGenerator(name="reservation_sequence", sequenceName = "reservation_sequence", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservation_sequence")
    private long id;
    private long bookId;
    private String userId;
    private long queuePosition;
    private LocalDate collectBy;

    public Reservation() { }

    public Reservation(long bookId, String userId, long queuePosition, LocalDate collectBy) {
        this.bookId = bookId;
        this.userId = userId;
        this.queuePosition = queuePosition;
        this.collectBy = collectBy;
    }

    public static class ReservationBuilder {
        public Reservation build() {
            return new Reservation(this.bookId, this.userId, this.queuePosition, this.collectBy);
        }
    }
}
