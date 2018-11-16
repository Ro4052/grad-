import React, { Component } from "react";
import { Button, Popup } from "semantic-ui-react";

import EditBook from "./EditBook";
import styles from "./Book.module.css";

export default class Book extends Component {
  constructor(props) {
    super(props);
    this.state = {
      fullTitle: false,
      fullAuthor: false
    };
    this.toggleTitle = this.toggleTitle.bind(this);
    this.toggleAuthor = this.toggleAuthor.bind(this);
  }

  toggleTitle() {
    this.setState({
      fullTitle: !this.state.fullTitle
    });
  }

  toggleAuthor() {
    this.setState({
      fullAuthor: !this.state.fullAuthor
    });
  }

  render() {
    const { book } = this.props;
    return (
      <li className={styles.book}>
        {this.props.deleteMode ? (
          <h3 id={`bookTitle${book.id}`}>
            <input
              id={book.id}
              className={styles.checkBox}
              type="checkbox"
              value={book.id}
              onClick={this.props.handleCheck}
            />
            <label htmlFor={book.id} className={styles.bookFieldBreak}>
              {book.title}
            </label>
          </h3>
        ) : (
          <h3
            id={`bookTitle${book.id}`}
            className={
              this.state.fullTitle
                ? styles.bookFieldBreak
                : styles.bookFieldEllipsis
            }
            onClick={this.toggleTitle}
          >
            {book.title}
          </h3>
        )}
        <div className={styles.bookDetails}>
          <div id={`isbn${book.id}`}>ISBN: {book.isbn}</div>
          <div
            id={`author${book.id}`}
            className={
              this.state.fullAuthor
                ? styles.bookFieldBreak
                : styles.bookFieldEllipsis
            }
            onClick={this.toggleAuthor}
          >
            Author: {book.author}
          </div>
          <div id={`publishDate${book.id}`}>
            Publish Date: {book.publishDate}
          </div>
        </div>
        <Popup
          className={styles.reservePopup}
          on="click"
          trigger={
            <Button primary onClick={() => this.props.reserveBook(book.id)}>
              Reserve
            </Button>
          }
          content={this.props.reservePopText}
        />
        {book.editState ? (
          <EditBook
            updateBook={this.props.updateBook}
            book={book}
            editStateChange={this.props.editStateChange}
          />
        ) : (
          <Button onClick={() => this.props.editStateChange(book.id)}>
            Edit
          </Button>
        )}
        <Popup
          className={styles.availablePopup}
          on="click"
          trigger={
            <Button onClick={() => this.props.checkBook(book.id)}>
              Check Availability
            </Button>
          }
          content={this.props.checkBookPopText}
        />
      </li>
    );
  }
}
