package com.scottlogic.librarygradproject.Entities;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Builder
@Data
@Entity
public class Borrow {

    @Id
    @SequenceGenerator(name="borrow_sequence", sequenceName = "borrow_sequence", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "borrow_sequence")
    private long id;
    private long bookId;
    private String userId;
    private Date borrowDate;
    private boolean isActive;

    public Borrow() {}

    public Borrow(long bookId, String userId, Date borrowDate, boolean isActive) {
        this.bookId = bookId;
        this.userId = userId;
        this.borrowDate = borrowDate;
        this.isActive = isActive;
    }

    public static class BorrowBuilder {
        public Borrow build() { return new Borrow(this.bookId, this.userId, this.borrowDate, this.isActive); }
    }

}
