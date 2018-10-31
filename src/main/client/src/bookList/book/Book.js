import React, { Component } from 'react'

export default class Book extends Component {
    render() {
        const {book} = this.props;
        return (
            <li>
                <h3>{book.title}</h3>
                <p>
                    id: {book.id},
                    author: {book.author},
                    isbn: {book.isbn},
                    publishDate: {book.publishDate}
                </p>
            </li>
        )
    }
}
