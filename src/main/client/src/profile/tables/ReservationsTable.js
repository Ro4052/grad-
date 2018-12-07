import React, { Component } from "react";
import { Table } from "semantic-ui-react";
import styles from "../Profile.module.css";
import RequestButton from "../../common/requestButton/RequestButton";
import buttonStates from "../../common/requestButton/buttonStates";

export default class ReservationsTable extends Component {
  render() {
    return (
      <Table celled>
        <Table.Header>
          <Table.Row>
            <Table.HeaderCell width={8}>Book Title</Table.HeaderCell>
            <Table.HeaderCell width={3}>Position in Queue</Table.HeaderCell>
            <Table.HeaderCell width={3}>Cancel Reservations</Table.HeaderCell>
          </Table.Row>
        </Table.Header>
        <Table.Body>
          {this.props.reservations.map(res => {
            const reservedBook = this.props.books.find(book => {
              return book.id === res.bookId;
            }) || {
              role: "Reserver",
              title: `Book with ID: ${
                res.bookId
              } has been removed from the library`
            };
            const buttonState =
              reservedBook && buttonStates(this.props, reservedBook);
            return (
              <Table.Row key={res.id}>
                <Table.Cell className={styles.tableCell}>
                  {reservedBook.title}
                </Table.Cell>
                <Table.Cell>{res.queuePosition}</Table.Cell>
                <Table.Cell>
                  <RequestButton
                    buttonState={buttonState}
                    cancelProcess={this.props.cancelProcess}
                    book={reservedBook}
                    content={reservedBook.popupText}
                    processStarted={reservedBook.processStarted}
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
