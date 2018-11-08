import React, { Component } from "react";
import EditBook from "./EditBook";

export default class Book extends Component {
  render() {
    const { book } = this.props;
    return (
      <li>
        <h3>
          {book.title}
          {this.props.deleteMode && (
            <label className="container">
              <input
                type="checkbox"
                value={book.id}
                onClick={this.props.handleCheck}
              />
              <span className="checkmark" />
            </label>
          )}
        </h3>
        <p>
          author: {book.author}, isbn: {book.isbn}, publishDate:{" "}
          {book.publishDate}
        </p>
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
