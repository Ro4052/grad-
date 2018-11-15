import React, { Component } from "react";
import Book from "./book/Book";
import { connect } from "react-redux";
import * as bookListActions from "./reducer";
import { bindActionCreators } from "redux";
import styles from "./BookList.module.css";

class BookList extends Component {
  render() {
    return (
      <div className={styles.bookListContainer}>
        <ul className={styles.bookList}>
          {this.props.books.map(book => (
            <Book
              deleteMode={this.props.deleteMode}
              handleCheck={this.props.handleCheck}
              updateBook={this.props.updateBook}
              editStateChange={this.props.editStateChange}
              reserveBook={this.props.reserveBook}
              key={book.id}
              book={book}
              reservePopText={this.props.reservePopText}
            />
          ))}
        </ul>
      </div>
    );
  }
}

const mapStateToProps = state => ({
  books: state.bookList.books,
  reservePopText: state.bookList.reservePopText
});

const mapDispatchToProps = dispatch =>
  bindActionCreators({ ...bookListActions }, dispatch);

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BookList);
