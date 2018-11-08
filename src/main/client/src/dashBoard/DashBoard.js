import React, { Component } from 'react';
import styles from "./DashBoard.module.css";
import BookList from './bookList/BookList';
import AddBook from './addBook/AddBook';
import DeleteBookModal from "../common/modals/DeleteBookModal";
import { connect } from 'react-redux';
import * as bookListActions from "./bookList/reducer";
import { bindActionCreators } from 'redux';

class DashBoard extends Component {
    constructor(props) {
        super(props)
        this.state = {
            deleteMode: false,
            deleteList: []
        }
        this.handleCheck = this.handleCheck.bind(this);
        this.toggleSelectMode = this.toggleSelectMode.bind(this);
    }

    handleCheck(event) {
        let newDeleteList = [...this.state.deleteList];
        event.target.checked
            ? newDeleteList.push(event.target.value)
            : newDeleteList = newDeleteList.filter(id => id !== event.target.value)
        this.setState({ deleteList: newDeleteList });
    }

    toggleSelectMode() {
        this.setState({ deleteMode: !this.state.deleteMode })
        this.setState({ deleteList: [] });
    }

    render() {
        return (
            <div>
                <div className={styles.navBar}>
                    <h1 className={styles.pageHeader}> Grad Library App </h1>
                    <button onClick={this.toggleSelectMode}>Select Books</button>
                </div>
                <BookList
                    deleteMode={this.state.deleteMode}
                    handleCheck={this.handleCheck}
                ></BookList>
                {this.state.deleteMode && this.state.deleteList.length > 0 &&
                    <DeleteBookModal
                        toggleSelectMode={this.toggleSelectMode}
                        deleteList={this.state.deleteList}
                        deleteBook={this.props.deleteBook}
                    />
                }
                <AddBook></AddBook>
            </div>
        )
    }
}

const mapDispatchToProps = dispatch => bindActionCreators(
    { ...bookListActions }, dispatch
);

export default connect(null, mapDispatchToProps)(DashBoard)
