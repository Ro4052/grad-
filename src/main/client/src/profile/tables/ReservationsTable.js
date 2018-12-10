import React, { Component } from "react";
import { Table, Popup } from "semantic-ui-react";
import styles from "../Profile.module.css";
import RequestButton from "../../common/requestButton/RequestButton";
import buttonStates from "../../common/requestButton/buttonStates";
import buttonCollectStates from "../../common/requestButton/buttonCollectStates";

export default class ReservationsTable extends Component {
  render() {
    return (
      <Table celled>
        <Table.Header>
          <Table.Row>
            <Table.HeaderCell width={8}>Book Title</Table.HeaderCell>
            <Table.HeaderCell width={2}>Position in Queue</Table.HeaderCell>
            <Table.HeaderCell width={2}>Cancel Reservations</Table.HeaderCell>
            <Table.HeaderCell width={2}>Collect Reservations</Table.HeaderCell>
          </Table.Row>
        </Table.Header>
        <Table.Body>
          {this.props.reservations.map(reservation => {
            const reservedBook = this.props.books.find(book => {
              return book.id === reservation.bookId;
            }) || {
              role: "Reserver",
              title: `Book with ID: ${
                reservation.bookId
              } has been removed from the library`
            };
            const buttonState =
              reservedBook && buttonStates(this.props, reservedBook);
            const collectState =
              reservedBook && buttonCollectStates(this.props, reservedBook);
            return (
              <Table.Row key={reservation.id}>
                <Table.Cell className={styles.tableCell}>
                  {reservedBook.title}
                </Table.Cell>
                <Table.Cell>{reservation.queuePosition}</Table.Cell>
                <Table.Cell textAlign="center">
                  <RequestButton
                    buttonState={buttonState}
                    book={reservedBook}
                    state={reservedBook.state}
                    cancelProcess={this.props.cancelProcess}
                  />
                </Table.Cell>
                <Table.Cell textAlign="center">
                  {(reservation.queuePosition === 1 &&
                    reservation.collectBy && (
                      <RequestButton
                        buttonState={collectState}
                        book={reservedBook}
                        state={reservedBook.collectState}
                        cancelProcess={this.props.cancelCollection}
                      />
                    )) || (
                    <Popup
                      on="hover"
                      trigger={<div className={styles.fakeButton}>Collect</div>}
                      content="This book is not ready for collection"
                      hideOnScroll
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
