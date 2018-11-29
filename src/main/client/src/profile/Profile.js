import React, { Component } from "react";
import { connect } from "react-redux";
import { Table } from "semantic-ui-react";

import history from "../history";
import PageHeader from "../common/pageHeader/PageHeader";
import styles from "./Profile.module.css";

class Profile extends Component {
  componentDidUpdate() {
    if (!this.props.loggedIn) {
      history.push("/dashboard");
    }
  }

  render() {
    return (
      <div className={styles.profile}>
        <PageHeader user={this.props.user} loggedIn={this.props.loggedIn} />
        {this.props.user && (
          <div className={styles.profileInfoContainer}>
            <img
              src={this.props.user.avatarUrl}
              className={styles.avatarPic}
              alt=""
            />
            <h2 className={styles.username}>{this.props.user.name}</h2>
            <h4 className={styles.username}>
              user ID: {this.props.user.userId}
            </h4>
            <div className={styles.tablesContainer}>
              <h3>Active Loans:</h3>
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
                        </Table.Row>
                      );
                    })}
                </Table.Body>
              </Table>
              <h3>Reservations:</h3>
              <Table celled>
                <Table.Header>
                  <Table.Row>
                    <Table.HeaderCell width={8}>Book Title</Table.HeaderCell>
                    <Table.HeaderCell width={4}>
                      Position in Queue
                    </Table.HeaderCell>
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
              <h3>Loan History:</h3>
              <Table celled collapsing>
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
                      return borrow.active === false;
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
                        </Table.Row>
                      );
                    })}
                </Table.Body>
              </Table>
            </div>
          </div>
        )}
      </div>
    );
  }
}

const mapStateToProps = state => ({
  user: state.login.user.userDetails,
  reservations: state.login.user.reservations,
  borrows: state.login.user.borrows,
  loggedIn: state.login.loggedIn,
  books: state.bookList.books
});

export default connect(mapStateToProps)(Profile);
