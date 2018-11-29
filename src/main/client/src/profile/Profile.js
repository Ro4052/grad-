import React, { Component } from "react";
import { connect } from "react-redux";
import { Image } from "semantic-ui-react";

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
    console.log(this.props.reservations);
    console.log(this.props.borrows);
    return (
      <div className={styles.profile}>
        <PageHeader user={this.props.user} loggedIn={this.props.loggedIn} />
        {this.props.user && (
          <div className={styles.profileInfoContainer}>
            <Image
              circular
              centered
              size="small"
              src={this.props.user.avatarUrl}
            />
            <h2 className={styles.username}>{this.props.user.name}</h2>
            <h4 className={styles.username}>
              user ID: {this.props.user.userId}
            </h4>
            <h3>Active Loans:</h3>
            <ul>
              {this.props.books
                .filter(book => {
                  return book.role === "Borrower";
                })
                .map(book => {
                  return <li key={book.id}>{book.title}</li>;
                })}
            </ul>
            <h3>Reservations:</h3>
            <ul>
              {this.props.books
                .filter(book => {
                  return book.role === "Reserver";
                })
                .map(book => {
                  return <li key={book.id}>{book.title}</li>;
                })}
            </ul>
            <h3>Loan History:</h3>
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
