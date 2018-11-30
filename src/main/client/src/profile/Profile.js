import React, { Component } from "react";
import { connect } from "react-redux";
import { Tab } from "semantic-ui-react";
import { bindActionCreators } from "redux";

import * as bookListActions from "../dashBoard/bookList/reducer";
import history from "../history";
import PageHeader from "../common/pageHeader/PageHeader";
import LoansTable from "./tables/LoansTable";
import LoanHistoryTable from "./tables/LoanHistoryTable";
import ReservationsTable from "./tables/ReservationsTable";
import styles from "./Profile.module.css";

class Profile extends Component {
  componentDidUpdate() {
    if (!this.props.loggedIn) {
      history.replace("/dashboard");
    }
  }

  render() {
    const panes = [
      {
        menuItem: {
          content: "Active Loans",
          icon: "book",
          key: "Active Loans"
        },
        render: () => (
          <Tab.Pane>
            <LoansTable borrows={this.props.borrows} books={this.props.books} />
          </Tab.Pane>
        )
      },
      {
        menuItem: {
          content: "Reservations",
          icon: "clock outline",
          key: "Reservations"
        },
        render: () => (
          <Tab.Pane>
            <ReservationsTable
              reservations={this.props.reservations}
              books={this.props.books}
            />
          </Tab.Pane>
        )
      },
      {
        menuItem: {
          content: "Loan History",
          icon: "archive",
          key: "Loan History"
        },
        render: () => (
          <Tab.Pane>
            <LoanHistoryTable
              borrows={this.props.borrows}
              books={this.props.books}
              //put props for the one button here
            />
          </Tab.Pane>
        )
      }
    ];
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
            <Tab panes={panes} className={styles.tabContainer} />
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

const mapDispatchToProps = dispatch =>
  bindActionCreators({ ...bookListActions }, dispatch);

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Profile);
