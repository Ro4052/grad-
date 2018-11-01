package com.scottlogic.librarygradproject;

import lombok.Data;

@Data
public class FilledBookRepository extends BookRepository {

    FilledBookRepository() {
        this.add(Book.builder()
                .isbn("9780747532743")
                .title("Harry Potter and the Philosopher's Stone")
                .author("J. K. Rowling")
                .publishDate("1997")
                .build());
        this.add(Book.builder()
                .isbn("9780194230476")
                .title("A Tale of Two Cities")
                .author("Charles Dickens")
                .publishDate("1935")
                .build());
        this.add(Book.builder()
                .title("The Hobbit")
                .publishDate("1937")
                .isbn("1234567890")
                .author("J. R. R. Tolkien")
                .build());
        this.add(Book.builder()
                .title("The Lion, the Witch and the Wardrobe")
                .publishDate("1950")
                .isbn("0987654321")
                .author("C. S. Lewis")
                .build());
    }

}
