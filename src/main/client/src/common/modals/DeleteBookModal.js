import React, { Component } from 'react';
import { Button, Header, Icon, Modal } from 'semantic-ui-react';

export default class DeleteBookModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
            open: false
        };
        this.close = this.close.bind(this);
    }

    close = () => {
        this.setState({ open: false })
    }

    render() {
        return (
            <div>
                <Modal trigger={
                    <button id="delBookBtn" onClick={() => this.setState({open: true})}>
                        Delete Selected Book(s)
                    </button>
                } basic size='small' open={this.state.open}>
                    <Header id="modalHeader" icon='trash alternate' content='Delete Selected Books' />
                    <Modal.Content>
                        <p>
                            You are about to permanently delete the selected books. Do you wish to continue?
                        </p>
                    </Modal.Content>
                    <Modal.Actions>
                        <Button id="noBtn" basic color='red' inverted onClick={this.close}>
                            <Icon name='remove' />No
                        </Button>
                        <Button id="yesBtn" color='green' inverted onClick={() => {
                            this.close();
                            this.props.deleteBook(this.props.deleteList);
                            this.props.clearDeleteList();
                        }}>
                            <Icon name='checkmark' />Yes
                        </Button>
                    </Modal.Actions>
                </Modal>
            </div>
        )
    }
}
