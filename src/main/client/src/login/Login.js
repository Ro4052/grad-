import React, { Component } from "react";
import { connect } from "react-redux";
import { bindActionCreators } from "redux";
import { Button } from "semantic-ui-react";
import * as loginActions from "../login/reducer";

class Login extends Component {
  render() {
    return (
      <div>
        {this.props.loggedIn ? (
          <Button
            negative
            size="small"
            onClick={this.props.logOut}
            content="Logout"
          />
        ) : (
          <Button
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
