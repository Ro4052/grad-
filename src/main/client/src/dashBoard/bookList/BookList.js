import React, { Component } from 'react'
import Book from "./book/Book"
import { connect } from 'react-redux';
import * as bookListActions from "./reducer";
import { bindActionCreators } from 'redux';

class BookList extends Component {
    render() {
        return (
            <div>
                <ul>
                    {this.props.books.map((book) =>
                        <Book
                            deleteMode={this.props.deleteMode}
                            handleCheck={this.props.handleCheck}
                            updateBook={this.props.updateBook}
                            editStateChange={this.props.editStateChange}
                            key={book.id}
                            book={book}>
                        </Book>
                    )}
                </ul>
            </div>
        )
    }
}

const mapStateToProps = state => ({
    books: state.bookList.books
});

const mapDispatchToProps = dispatch => bindActionCreators(
    { ...bookListActions }, dispatch
);

export default connect(mapStateToProps, mapDispatchToProps)(BookList)