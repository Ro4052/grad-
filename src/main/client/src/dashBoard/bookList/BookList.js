import React, { Component } from "react";
import Book from "./book/Book";
import { connect } from "react-redux";
import * as bookListActions from "./reducer";
import { bindActionCreators } from "redux";
import styles from "./BookList.module.css";

export class BookList extends Component {
  render() {
    return (
      <div className={styles.bookListContainer}>
        <ul className={styles.bookList}>
          {this.props.books
            .filter(book => {
              book.all = `${book.title} ${book.author} ${book.isbn} ${
                book.publishDate
              }`;
              if (this.props.searchBy !== "publishDate") {
                return this.props.searchString.length > 0 &&
                  book[this.props.searchBy].length
                  ? book[this.props.searchBy]
                      .toLowerCase()
                      .includes(this.props.searchString.toLowerCase())
                  : book;
              } else {
                const lowerDate = Number(this.props.lowerDate) || 0;
                const upperDate =
                  Number(this.props.upperDate) || new Date().getFullYear();
                return (
                  (Number(book.publishDate) >= lowerDate &&
                    Number(book.publishDate) <= upperDate) ||
                  book.publishDate.length === 0
                );
              }
            })
            .map(book => (
              <Book
                deleteMode={this.props.deleteMode}
                handleCheck={this.props.handleCheck}
                updateBook={this.props.updateBook}
                editStateChange={this.props.editStateChange}
                key={book.id}
                book={book}
                reserveBook={this.props.reserveBook}
                popupText={this.props.popupText}
                checkBook={this.props.checkBook}
                borrowBook={this.props.borrowBook}
                loggedIn={this.props.loggedIn}
              />
            ))}
        </ul>
      </div>
    );
  }
}

const mapStateToProps = state => ({
  books: state.bookList.books,
  popupText: state.bookList.popupText,
  loggedIn: state.login.loggedIn
});

const mapDispatchToProps = dispatch =>
  bindActionCreators({ ...bookListActions }, dispatch);

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BookList);
