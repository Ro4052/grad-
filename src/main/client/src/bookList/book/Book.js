import React, { Component } from 'react';
import { connect } from "react-redux";
import * as bookListActions from "../reducer";
import { bindActionCreators } from 'redux';
import EditBook from "./EditBook";

class Book extends Component {
    render() {
        const { book } = this.props;
        return (
            <li>
                <h3>{book.title}</h3>
                <p>
                    id: {book.id},
                    author: {book.author},
                    isbn: {book.isbn},
                    publishDate: {book.publishDate}
                </p>
                {book.editState ? <EditBook book={book}></EditBook>: <button onClick={() => this.props.editStateChange(book.id)}>Edit</button>}
            </li>
        )
    }
}

const mapDispatchToProps = dispatch => bindActionCreators(
    {...bookListActions}, dispatch
);

export default connect(
    null,
    mapDispatchToProps
)(Book)