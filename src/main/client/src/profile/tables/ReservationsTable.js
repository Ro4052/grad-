import React, { Component } from "react";
import { Table } from "semantic-ui-react";
import styles from "../Profile.module.css";
import RequestButton from "../../dashBoard/bookList/book/requestButton/RequestButton";

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
            });
            let request, colour, buttonText;
            if (!reservedBook.processStarted) {
              request = this.props.startProcess;
              colour = "red";
              buttonText = "Cancel";
            } else {
              request = this.props.cancelReservation;
              colour = "red";
              buttonText = "Confirm";
            }
            return (
              <Table.Row key={res.id}>
                <Table.Cell className={styles.tableCell}>
                  {reservedBook.title}
                </Table.Cell>
                <Table.Cell>{res.queuePosition}</Table.Cell>
                <Table.Cell>
                  <RequestButton
                    request={request}
                    colour={colour}
                    buttonText={buttonText}
                    cancelProcess={this.props.cancelProcess}
                    book={reservedBook}
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
