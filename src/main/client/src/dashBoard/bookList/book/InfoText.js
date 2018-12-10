import React, { Component } from "react";
import { Icon, Popup } from "semantic-ui-react";

const moment = require("moment");

export default class InfoText extends Component {
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
            content={
              book.state === "Available to Borrow"
                ? `You can borrow this book until ${moment()
                    .add(7, "days")
                    .format("ddd Do MMM YYYY")}`
                : book.state === "Borrower - Return Not Started"
                ? `You must return this book by ${moment(
                    borrow.returnDate
                  ).format("ddd Do MMM YYYY")}`
                : `You must collect this book by ${moment(
                    reservation.collectBy
                  ).format(
                    "ddd Do MMM YYYY"
                  )}, else your reservation will be cancelled`
            }
            hideOnScroll
          />
        )}
      </div>
    );
  }
}
