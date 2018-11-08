import React, { Component } from "react";
import EditBook from "./EditBook";
import styles from "./Book.module.css";

export default class Book extends Component {
  render() {
    const { book } = this.props;
    return (
      <li className={styles.book}>
        {this.props.deleteMode ? (
          <h3 className={styles.bookTitle}>
            <input
              id={book.id}
              className={styles.checkBox}
              type="checkbox"
              value={book.id}
              onClick={this.props.handleCheck}
            />
            <label htmlFor={book.id}>{book.title}</label>
          </h3>
        ) : (
          <h3 className={styles.bookTitle}>{book.title}</h3>
        )}
        <div className={styles.bookDetails}>
          <div>author: {book.author}</div>
          <div>isbn: {book.isbn}</div>
          <div>publishDate: {book.publishDate}</div>
        </div>
        {book.editState ? (
          <EditBook
            updateBook={this.props.updateBook}
            book={book}
            editStateChange={this.props.editStateChange}
          />
        ) : (
          <button onClick={() => this.props.editStateChange(book.id)}>
            Edit
          </button>
        )}
      </li>
    );
  }
}
