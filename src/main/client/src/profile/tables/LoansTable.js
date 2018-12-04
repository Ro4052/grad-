import React, { Component } from "react";
import { Table } from "semantic-ui-react";
import styles from "../Profile.module.css";
import RequestButton from "../../common/requestButton/RequestButton";

export default class LoansTable extends Component {
  render() {
    return (
      <Table celled>
        <Table.Header>
          <Table.Row>
            <Table.HeaderCell width={8}>Book Title</Table.HeaderCell>
            <Table.HeaderCell width={2}>Date Borrowed</Table.HeaderCell>
            <Table.HeaderCell width={2}>Due Date</Table.HeaderCell>
            <Table.HeaderCell width={2}>Return Books</Table.HeaderCell>
          </Table.Row>
        </Table.Header>
        <Table.Body>
          {this.props.borrows
            .filter(borrow => {
              return borrow.active === true;
            })
            .map(borrow => {
              const borrowedBook = this.props.books.find(book => {
                return book.id === borrow.bookId;
              });
              let request,
                colour,
                buttonText,
                disabled = false;
              if (!borrowedBook) {
                request = () => {};
                buttonText = "Deleted";
                disabled = true;
              } else if (!borrowedBook.processStarted) {
                request = this.props.startProcess;
                colour = "red";
                buttonText = "Return";
              } else {
                request = this.props.returnBook;
                colour = "red";
                buttonText = "Confirm";
              }
              return (
                <Table.Row key={borrow.id}>
                  <Table.Cell className={styles.tableCell}>
                    {borrowedBook ? borrowedBook.title : "Book deleted"}
                  </Table.Cell>
                  <Table.Cell>{borrow.borrowDate}</Table.Cell>
                  <Table.Cell>{borrow.returnDate}</Table.Cell>
                  <Table.Cell>
                    <RequestButton
                      request={request}
                      colour={colour}
                      buttonText={buttonText}
                      cancelProcess={this.props.cancelProcess}
                      book={borrowedBook}
                      disabled={disabled}
                    />
                  </Table.Cell>
                </Table.Row>
              );
            })}
        </Table.Body>
      </Table>
    );
  }
}
