import React, { Component } from 'react';
import styles from "./DashBoard.module.css";
import BookList from './bookList/BookList';
import AddBook from './addBook/AddBook';

export default class DashBoard extends Component {
    constructor(props) {
		super(props)
		this.state = {
			deleteMode: false
		}
    }
    
    render() {
        return (
            <div>
                <div className={styles.navBar}>
					<h1 className={styles.pageHeader}> Grad Library App </h1>
					<button>Select Books</button>
				</div>
				<BookList deleteMode={this.state.deleteMode}></BookList>
				<AddBook></AddBook>
            </div>
        )
    }
}
