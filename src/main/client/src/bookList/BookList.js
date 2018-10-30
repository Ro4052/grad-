import React, { Component } from 'react'
import Book from "./book/Book"

export default class BookList extends Component {
    render() {
        return (
            <ul>
                {this.props.books.map((book) =>
                    <Book key={book.id} book={book}></Book>
                )}
            </ul>
        )
    }
}
