import React, { Component } from "react";
import { Table } from "semantic-ui-react";
import styles from "../Profile.module.css";

export default class ReservationsTable extends Component {
  render() {
    return (
      <Table celled>
        <Table.Header>
          <Table.Row>
            <Table.HeaderCell width={8}>Book Title</Table.HeaderCell>
            <Table.HeaderCell width={4}>Position in Queue</Table.HeaderCell>
          </Table.Row>
        </Table.Header>
        <Table.Body>
          {this.props.reservations.map(res => {
            return (
              <Table.Row key={res.id}>
                <Table.Cell className={styles.tableCell}>
                  {
                    this.props.books.find(book => {
                      return book.id === res.bookId;
                    }).title
                  }
                </Table.Cell>
                <Table.Cell>{res.queuePosition}</Table.Cell>
              </Table.Row>
            );
          })}
        </Table.Body>
      </Table>
    );
  }
}
