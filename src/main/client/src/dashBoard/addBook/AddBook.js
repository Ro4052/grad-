import React, { Component } from "react";
import * as axios from "axios";
import BookForm from "../../common/BookForm";
import styles from "./AddBook.module.css";

export default class AddBook extends Component {
  constructor(props) {
    super(props);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleSubmit(newBook) {
    axios.post("/api/books", newBook);
  }

  render() {
    const newBook = {
      id: "",
      isbn: "",
      title: "",
      author: "",
      publishDate: ""
    };
    return (
      <div className={styles.addBook}>
        <BookForm
          book={newBook}
          buttonText="Add Book"
          handleSubmit={this.handleSubmit}
        />
      </div>
    );
  }
}
