import React, { Component } from 'react'
import * as axios from "axios";
import BookForm from "../../common/BookForm"

export default class EditBook extends Component {

    constructor(props) {
        super(props);
        this.state = {
            isbn: this.props.book.isbn,
            title: this.props.book.title,
            author: this.props.book.author,
            publishDate: this.props.book.publishDate,
            buttonText: "Save changes"
        }
        this.handleChange = this.handleChange.bind(this);
        this.submitForm = this.submitForm.bind(this);
    }

    handleChange(evt) {
        this.setState({ [evt.target.id]: evt.target.value.trimStart() });
        if (evt.target.id === "publishDate" && evt.target.value.length > 0) { evt.target.value = evt.target.value.slice(0, 4) };
    }

    async submitForm(evt) {
        evt.preventDefault();
        let newBook = {
            "isbn": this.state.isbn.trim(),
            "title": this.state.title.trim(),
            "author": this.state.author.trim(),
            "publishDate": this.state.publishDate.trim()
        }
        await axios.put(`/api/books/${this.props.book.id}`, newBook);
    }

    render() {
        return (
            <div>
                <form onSubmit={this.submitForm}>
                    <BookForm state={this.state} handleChange={this.handleChange} />
                </form>
            </div>
        )
    }
}