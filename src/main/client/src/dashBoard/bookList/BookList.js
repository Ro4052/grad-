import React, { Component } from 'react'
import Book from "./book/Book"
import { connect } from 'react-redux';
import * as bookListActions from "./reducer";
import { bindActionCreators } from 'redux';
import DeleteBookModal from "../../common/modals/DeleteBookModal";

class BookList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            deleteList: []
        };
        this.handleCheck = this.handleCheck.bind(this);
        this.clearDeleteList = this.clearDeleteList.bind(this);
    }

    clearDeleteList() {
        this.setState({deleteList: []});
    }

    handleCheck(event) {
        let newDeleteList = [...this.state.deleteList];
        event.target.checked
            ? newDeleteList.push(event.target.value)
            : newDeleteList = newDeleteList.filter(id => id !== event.target.value)
        this.setState({ deleteList: newDeleteList });
    }

    render() {
        return (
            <div>
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
                <DeleteBookModal
                    clearDeleteList={this.clearDeleteList}
                    deleteList={this.state.deleteList}
                    deleteBook={this.props.deleteBook}
                />
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