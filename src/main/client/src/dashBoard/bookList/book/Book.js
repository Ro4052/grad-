import React, { Component } from "react";
import { Button } from "semantic-ui-react";

import EditBook from "./EditBook";
import styles from "./Book.module.css";
import RequestButton from "../../../common/requestButton/RequestButton";
import buttonLogic from "../../../common/requestButton/buttonLogic";

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
    let request, colour, buttonText;
    const { book } = this.props;
    const data = buttonLogic(this.props, book);
    const reservation = this.props.user.reservations.find(res => {
      return res.bookId === book.id;
    });
    colour = "green";
    if (!book.collectionStarted) {
      request = this.props.startCollection;
      buttonText = "Collect";
    } else {
      request = this.props.collectBook;
      buttonText = "Confirm";
    }
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
              request={data.request}
              colour={data.colour}
              buttonText={data.buttonText}
              book={book}
              cancelProcess={this.props.cancelProcess}
              content={book.popupText}
              processStarted={this.props.book.processStarted}
            />
            {reservation &&
              reservation.queuePosition === 1 &&
              reservation.collectBy && (
                <RequestButton
                  request={request}
                  colour={colour}
                  buttonText={buttonText}
                  book={book}
                  collectionStarted={this.props.collectionStarted}
                  cancelProcess={this.props.cancelCollection}
                  processStarted={this.props.book.collectionStarted}
                  content={book.collectPopupText}
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
