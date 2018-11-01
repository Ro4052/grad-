import React, { Component } from 'react';
import BookForm from "../../common/BookForm";

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
        if (evt.target.id === "publishDate" && evt.target.value.length > 0) { evt.target.value = evt.target.value.slice(0, 4) };
        this.setState({ [evt.target.id]: evt.target.value.trimStart() });
    }

    submitForm(evt) {
        evt.preventDefault();
        const updatedBook = {
            "id": this.props.book.id,
            "isbn": this.state.isbn.trim(),
            "title": this.state.title.trim(),
            "author": this.state.author.trim(),
            "publishDate": this.state.publishDate.trim(),
            "editState": false
        }
        this.props.updateBook(updatedBook);
    }

    render() {
        return (
            <div>
                <form className="test" onSubmit={this.submitForm}>
                    <BookForm state={this.state} handleChange={this.handleChange} />
                </form>
            </div>
        )
    }
}