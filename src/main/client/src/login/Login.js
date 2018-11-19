import React, { Component } from "react";
import { connect } from "react-redux";
import { bindActionCreators } from "redux";
import { Button } from "semantic-ui-react";
import styles from "./Login.module.css";
import * as loginActions from "../login/reducer";

class Login extends Component {
  render() {
    return (
      <div>
        {this.props.loggedIn ? (
          <Button
            className={styles.logBtns}
            negative
            onClick={this.props.logOut}
            content="Logout"
          />
        ) : (
          <Button
            className={styles.logBtns}
            positive
            size="small"
            onClick={this.props.loginUser}
            content="Login"
          />
        )}
      </div>
    );
  }
}

const mapStateToProps = state => ({
  loggedIn: state.login.loggedIn
});

const mapDispatchToProps = dispatch =>
  bindActionCreators({ ...loginActions }, dispatch);

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Login);
