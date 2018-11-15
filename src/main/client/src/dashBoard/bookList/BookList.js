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
            .filter(book =>
              this.props.searchString.length > 0
                ? book.title.includes(this.props.searchString)
                : book
            )
            .map(book => (
              <Book
                deleteMode={this.props.deleteMode}
                handleCheck={this.props.handleCheck}
                updateBook={this.props.updateBook}
                editStateChange={this.props.editStateChange}
                key={book.id}
                book={book}
              />
            ))}
        </ul>
      </div>
    );
  }
}

const mapDispatchToProps = dispatch =>
  bindActionCreators({ ...bookListActions }, dispatch);

export default connect(
  null,
  mapDispatchToProps
)(BookList);
