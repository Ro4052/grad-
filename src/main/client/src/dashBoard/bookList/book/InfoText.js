import React, { Component } from "react";
import { Icon, Popup } from "semantic-ui-react";

const moment = require("moment");

export default class InfoText extends Component {
  chooseText(book, reservation, borrow) {
    switch (book.state) {
      case "Available to Borrow":
        return `You can borrow this book until ${moment()
          .add(7, "days")
          .format("ddd Do MMM YYYY")}`;
      case "Borrower - Return Not Started":
        return `You must return this book by ${moment(borrow.returnDate).format(
          "ddd Do MMM YYYY"
        )}`;
      default:
        return `You must collect this book by ${moment(
          reservation.collectBy
        ).format("ddd Do MMM YYYY")}, else your reservation will be cancelled`;
    }
  }

  render() {
    const { book, borrow, reservation } = this.props;
    return (
      <div>
        {(book.collectState === "Collector - Collection Not Started" ||
          book.state === "Borrower - Return Not Started" ||
          book.state === "Available to Borrow") && (
          <Popup
            on="hover"
            position="top center"
            verticalOffset={10}
            trigger={<Icon name="question" />}
            content={this.chooseText(book, reservation, borrow)}
            hideOnScroll
          />
        )}
      </div>
    );
  }
}
