import React, { Component } from "react";
import { Button, Popup } from "semantic-ui-react";

export default class RequestButton extends Component {
  render() {
    return (
      <Popup
        on="hover"
        trigger={
          <Button
            id="variableButton"
            onClick={() => this.props.request(this.props.bookId)}
            color={this.props.colour}
          >
            {this.props.buttonText}
          </Button>
        }
        content={this.props.popupText}
        hideOnScroll
      />
    );
  }
}
