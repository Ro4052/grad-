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
    if (
      this.props.state === "Borrower - Return Started" ||
      this.props.state === "Reserver - Cancel Started" ||
      this.props.state === "Available to Borrow" ||
      this.props.state === "Available to Reserve" ||
      this.props.state === "Collector - Collection Started"
    ) {
      document.addEventListener("click", this.checkClick);
    }
  }

  componentWillUnmount() {
    document.removeEventListener("click", this.checkClick);
  }

  render() {
    return (
      <div
        ref={el => {
          this.confirmButton = el;
        }}
      >
        {this.props.buttonState && (
          <Popup
            on="hover"
            trigger={
              <Button
                id="variableButton"
                onClick={() => this.props.buttonState.request(this.props.book)}
                color={this.props.buttonState.colour}
                basic={this.props.buttonState.basic}
                disabled={this.props.buttonState.disabled}
              >
                {this.props.buttonState.buttonText}
              </Button>
            }
            content={this.props.buttonState.popupText}
            hideOnScroll
          />
        )}
      </div>
    );
  }
}
