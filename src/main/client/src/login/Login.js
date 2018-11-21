import React, { Component } from "react";
import { connect } from "react-redux";
import { bindActionCreators } from "redux";
import { Button } from "semantic-ui-react";
import * as loginActions from "../login/reducer";

class Login extends Component {
  render() {
    const { loggedIn, logOut, loginUser } = this.props;
    const onClick = loggedIn ? logOut : loginUser;
    const content = loggedIn ? "Logout" : "Login";
    return (
      <div>
        <Button
          positive={!loggedIn}
          negative={loggedIn}
          size="small"
          onClick={onClick}
          content={content}
        />
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
