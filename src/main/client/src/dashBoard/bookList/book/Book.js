import React, { Component } from "react";
import { Button } from "semantic-ui-react";

import EditBook from "./EditBook";
import styles from "./Book.module.css";
import RequestButton from "../../../common/requestButton/RequestButton";
import buttonStates from "../../../common/requestButton/buttonStates";
import buttonCollectStates from "../../../common/requestButton/buttonCollectStates";

export default class Book extends Component {
  constructor(props) {
    super(props);
    this.state = {
      fullTitle: false,
      fullAuthor: false,
      availabilityChecked: false
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
    const buttonState = buttonStates(this.props, book);
    const collectState = buttonCollectStates(this.props, book);
    const reservation = this.props.userReservations.find(res => {
      return res.bookId === book.id;
    });
    return (
      <li className={styles.book}>
        {this.props.deleteMode && this.props.loggedIn ? (
          <h3 id={`bookTitle${book.id}`}>
            <input
              id={book.id}
              className={styles.checkBox}
              type="checkbox"
              value={book.id}
              checked={this.props.checked}
              onChange={this.props.handleCheck}
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
        {this.props.loggedIn && (
          <div className={styles.bookBtnsContainer}>
            <RequestButton
              buttonState={buttonState}
              book={book}
              state={book.state}
              cancelProcess={this.props.cancelProcess}
            />
            {reservation &&
              reservation.queuePosition === 1 &&
              reservation.collectBy && (
                <RequestButton
                  buttonState={collectState}
                  book={book}
                  state={book.collectState}
                  cancelProcess={this.props.cancelCollection}
                />
              )}
            {book.editState ? (
              <EditBook
                updateBook={this.props.updateBook}
                book={book}
                editStateChange={this.props.editStateChange}
              />
            ) : (
              <Button
                id="editButton"
                onClick={() => this.props.editStateChange(book.id)}
              >
                Edit
              </Button>
            )}
          </div>
        )}
      </li>
    );
  }
}
