package com.scottlogic.librarygradproject.Entities;

import com.scottlogic.librarygradproject.Exceptions.IncorrectBookFormatException;
import lombok.Builder;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Optional;

@Builder
@Entity
public class Book {
    @Id
    @GeneratedValue
    private long id;
    private String isbn;
    private String title;
    private String author;
    private String publishDate;

    public Book() { }

    public Book(String isbn, String title, String author, String publishDate) {
        this.isbn = isbn;
        setTitle(title);
        setAuthor(author);
        this.publishDate = publishDate;
    }

    public static class BookBuilder {
        public Book build() {
            return new Book(this.isbn, this.title, this.author, this.publishDate);
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        isbn = Optional.ofNullable(isbn).orElse("").trim();
        if (!isbn.matches("|[0-9]{10}|[0-9]{13}")) {
            throw new IncorrectBookFormatException();
        }
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        title = Optional.ofNullable(title).orElseThrow(() -> new IncorrectBookFormatException()).trim();
        if (title.length() == 0 || title.length() > 200) {
            throw new IncorrectBookFormatException();
        }
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        author = Optional.ofNullable(author).orElseThrow(() -> new IncorrectBookFormatException()).trim();
        if (author.length() == 0 || author.length() > 200) {
            throw new IncorrectBookFormatException();
        }
        this.author = author;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (isbn != null ? !isbn.equals(book.isbn) : book.isbn != null) return false;
        if (title != null ? !title.equals(book.title) : book.title != null) return false;
        if (author != null ? !author.equals(book.author) : book.author != null) return false;
        return publishDate != null ? publishDate.equals(book.publishDate) : book.publishDate == null;
    }

    @Override
    public int hashCode() {
        int result = (int) id;
        result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (publishDate != null ? publishDate.hashCode() : 0);
        return result;
    }
}
