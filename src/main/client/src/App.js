import React, { Component } from 'react';
import axios from 'axios';

class App extends Component {
  constructor() {
    super();
    this.state = {
      books: []
    };
    this.getBooks();
  }

  getBooks() {
    axios.get('/api/books').then((res) => {
      this.setState({
        books: res.data
      });
    });
  }

  render() {
    return (
      <div>
        <h1> Grad Library App </h1>
        <ul>
          {this.state.books.map((book) =>
            <li key={book.id}>
              <h3> {book.title} </h3>
              <p>
                id: {book.id},
                author: {book.author},
                isbn: {book.isbn},
                publishDate: {book.publishDate}
              </p>
            </li>
          )}
        </ul>
      </div>
    );
  }
}

export default App;
