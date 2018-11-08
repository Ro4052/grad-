import React, { Component } from "react";
import { Button, Header, Icon, Modal } from "semantic-ui-react";

export default class DeleteBookModal extends Component {
  constructor(props) {
    super(props);
    this.state = {
      open: false
    };
    this.close = this.close.bind(this);
    this.callDeleteBook = this.callDeleteBook.bind(this);
  }

  close() {
    this.setState({ open: false });
  }

  callDeleteBook() {
    this.close();
    this.props.deleteBook(this.props.deleteList);
    this.props.toggleSelectMode();
  }

  render() {
    return (
      <div>
        <Modal
          id="modal"
          trigger={
            <Button
              disabled={!(this.props.deleteList.length > 0)}
              id="delBookBtn"
              onClick={() => this.setState({ open: true })}
              style={{ marginRight: "1em" }}
            >
              Delete Selected Book(s)
            </Button>
          }
          basic
          size="small"
          open={this.state.open}
        >
          <Header
            id="modalHeader"
            icon="trash alternate"
            content="Delete Selected Books"
          />
          <Modal.Content>
            <p>
              You are about to permanently delete the selected books. Do you
              wish to continue?
            </p>
          </Modal.Content>
          <Modal.Actions>
            <Button id="noBtn" inverted onClick={this.close}>
              <Icon name="remove" />
              Cancel
            </Button>
            <Button
              id="yesBtn"
              color="red"
              inverted
              onClick={this.callDeleteBook}
            >
              <Icon name="checkmark" />
              Delete
            </Button>
          </Modal.Actions>
        </Modal>
      </div>
    );
  }
}
