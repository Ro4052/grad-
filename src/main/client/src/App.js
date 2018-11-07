import React, { Component } from "react";

import styles from "./App.module.css";
import BookList from "./bookList/BookList";
import AddBook from "./addBook/AddBook";

export default class App extends Component {
  render() {
    return (
      <div>
        <h1 className={styles.pageHeader}> Grad Library App </h1>
        <BookList />
        <AddBook />
      </div>
    );
  }
}
