import React, { Component } from "react";
import BookForm from "../../../common/BookForm";

export default class EditBook extends Component {
  render() {
    return (
      <div>
        <BookForm
          book={this.props.book}
          buttonText="Save changes"
          handleSubmit={this.props.updateBook}
        />
      </div>
    );
  }
}
