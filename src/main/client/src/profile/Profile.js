import React, { Component } from "react";
import { connect } from "react-redux";

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
        <div className={styles.profileInfoContainer}>Info</div>
      </div>
    );
  }
}

const mapStateToProps = state => ({
  user: state.login.user,
  loggedIn: state.login.loggedIn
});

export default connect(mapStateToProps)(Profile);
