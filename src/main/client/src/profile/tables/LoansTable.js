import React, { Component } from "react";
import { Table } from "semantic-ui-react";
import styles from "../Profile.module.css";
import RequestButton from "../../dashBoard/bookList/book/requestButton/RequestButton";

export default class LoansTable extends Component {
  render() {
    return (
      <Table celled>
        <Table.Header>
          <Table.Row>
            <Table.HeaderCell width={8}>Book Title</Table.HeaderCell>
            <Table.HeaderCell width={2}>Date Borrowed</Table.HeaderCell>
            <Table.HeaderCell width={2}>Due Date</Table.HeaderCell>
          </Table.Row>
        </Table.Header>
        <Table.Body>
          {this.props.borrows
            .filter(borrow => {
              return borrow.active === true;
            })
            .map(borrow => {
              return (
                <Table.Row key={borrow.id}>
                  <Table.Cell className={styles.tableCell}>
                    {
                      this.props.books.find(book => {
                        return book.id === borrow.bookId;
                      }).title
                    }
                  </Table.Cell>
                  <Table.Cell>{borrow.borrowDate}</Table.Cell>
                  <Table.Cell>{borrow.returnDate}</Table.Cell>
                  <Table.Cell>
                    {/* put relevant logic for button here */}
                    {/* <RequestButton
                      request={request}
                      colour={colour}
                      buttonText={buttonText}
                      book={book}
                      cancelProcess={this.props.cancelProcess}
                    /> */}
                  </Table.Cell>
                </Table.Row>
              );
            })}
        </Table.Body>
      </Table>
    );
  }
}
