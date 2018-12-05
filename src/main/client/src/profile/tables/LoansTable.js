import React, { Component } from "react";
import { Table } from "semantic-ui-react";
import styles from "../Profile.module.css";
import RequestButton from "../../common/requestButton/RequestButton";
import buttonLogic from "../../common/requestButton/buttonLogic";

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
              }) || {
                role: "Borrower",
                title: `Book with ID: ${
                  borrow.bookId
                } has been removed from the library`
              };
              const data =
                borrowedBook && buttonLogic(this.props, borrowedBook);
              return (
                <Table.Row key={borrow.id}>
                  <Table.Cell className={styles.tableCell}>
                    {borrowedBook ? borrowedBook.title : "Book deleted"}
                  </Table.Cell>
                  <Table.Cell>{borrow.borrowDate}</Table.Cell>
                  <Table.Cell>{borrow.returnDate}</Table.Cell>
                  <Table.Cell>
                    {data && (
                      <RequestButton
                        request={data.request}
                        colour={data.colour}
                        buttonText={data.buttonText}
                        disabled={data.disabled}
                        cancelProcess={this.props.cancelProcess}
                        book={borrowedBook}
                        content={borrowedBook.popupText}
                      />
                    )}
                  </Table.Cell>
                </Table.Row>
              );
            })}
        </Table.Body>
      </Table>
    );
  }
}
