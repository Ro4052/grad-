import React, { Component } from "react";
import styles from "./BookForm.module.css";

export default class BookForm extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isbn: this.props.book.isbn,
      title: this.props.book.title,
      author: this.props.book.author,
      publishDate: this.props.book.publishDate,
      buttonText: this.props.buttonText
    };
    this.handleChange = this.handleChange.bind(this);
    this.submitForm = this.submitForm.bind(this);
  }

  handleChange(evt) {
    if (evt.target.id === "publishDate" && evt.target.value.length > 0) {
      evt.target.value = evt.target.value.trimLeft().slice(0, 4);
    }
    this.setState({ [evt.target.id]: evt.target.value.trimLeft() });
  }

  submitForm(evt) {
    let year = this.state.publishDate.trim();
    if (year.length > 0) {
      while (year.length < 4) {
        year = "0" + year;
      }
    }
    evt.preventDefault();
    const book = {
      id: this.props.book.id,
      isbn: this.state.isbn.trim(),
      title: this.state.title.trim(),
      author: this.state.author.trim(),
      publishDate: year
    };
    this.props.handleSubmit(book);
    this.setState({
      isbn: "",
      title: "",
      author: "",
      publishDate: ""
    });
  }

  render() {
    let date = new Date().getFullYear();
    return (
      <form onSubmit={this.submitForm}>
        <div className={styles.formItem}>
          <label className={styles.formLabel}>ISBN:</label>
          <input
            className={styles.formInput}
            type="text"
            pattern="^[0-9]{10}|[0-9]{13}$"
            maxLength="13"
            placeholder="ISBN"
            value={this.state.isbn}
            id="isbn"
            onChange={this.handleChange}
          />
        </div>
        <div className={styles.formItem}>
          <label className={styles.formLabel}>Title:</label>
          <input
            className={styles.formInput}
            type="text"
            placeholder="Title"
            value={this.state.title}
            id="title"
            maxLength="200"
            required
            onChange={this.handleChange}
          />
        </div>
        <div className={styles.formItem}>
          <label className={styles.formLabel}>Author:</label>
          <input
            className={styles.formInput}
            type="text"
            placeholder="Author"
            value={this.state.author}
            id="author"
            maxLength="200"
            required
            onChange={this.handleChange}
          />
        </div>
        <div className={styles.formItem}>
          <label className={styles.formLabel}>Publish Date:</label>
          <input
            className={styles.formInput}
            type="number"
            placeholder="Publish Date"
            value={this.state.publishDate}
            id="publishDate"
            pattern="^[0-9]{4}$"
            max={date}
            onChange={this.handleChange}
          />
        </div>
        <div className={styles.formItem}>
          <button type="submit" id="submitButton">
            {this.state.buttonText}
          </button>
        </div>
      </form>
    );
  }
}
