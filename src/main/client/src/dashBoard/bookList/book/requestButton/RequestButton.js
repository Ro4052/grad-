import React, { Component } from "react";
import { Button, Popup } from "semantic-ui-react";

export default class RequestButton extends Component {
  constructor(props) {
    super(props);
    this.checkClick = this.checkClick.bind(this);
  }

  checkClick(evt) {
    if (evt.target !== this.confirmButton.childNodes[0]) {
      this.props.cancelProcess(this.props.book);
    }
    document.removeEventListener("click", this.checkClick);
  }

  componentDidUpdate() {
    if (this.props.book.processStarted) {
      document.addEventListener("click", this.checkClick);
    }
  }

  render() {
    return (
      <div
        ref={el => {
          this.confirmButton = el;
        }}
      >
        <Popup
          on="hover"
          trigger={
            <Button
              id="variableButton"
              onClick={() => this.props.request(this.props.book)}
              color={this.props.colour}
              basic={this.props.book.processStarted}
            >
              {this.props.buttonText}
            </Button>
          }
          content={this.props.book.popupText}
          hideOnScroll
        />
      </div>
    );
  }
}
