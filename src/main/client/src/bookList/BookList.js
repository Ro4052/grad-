import React, { Component } from 'react'
import Book from "./book/Book"
import { connect } from 'react-redux';
import * as bookListActions from "./reducer";
import { bindActionCreators } from 'redux';

class BookList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            deleteList: []
        };
        this.handleCheck = this.handleCheck.bind(this);
    }

    handleCheck(event) {
        let newDeleteList = [...this.state.deleteList];
        event.target.checked
            ? newDeleteList.push(event.target.value)
            : newDeleteList = newDeleteList.filter(id => id !== event.target.value)
        this.setState({ deleteList: newDeleteList });
    }

    render() {
        console.log(this.state.deleteList)
        return (
            <ul>
                {this.props.books.map((book) =>
                    <Book
                        handleCheck={this.handleCheck}
                        updateBook={this.props.updateBook}
                        editStateChange={this.props.editStateChange}
                        key={book.id}
                        book={book}>
                    </Book>
                )}
            </ul>
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