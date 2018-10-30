import React, { Component } from 'react';
import axios from 'axios';
import { connect } from 'react-redux'

import styles from './App.module.css';
import BookList from './bookList/BookList';

class App extends Component {
	render() {
		return (
			<div>
				<h1 className={styles.pageHeader}> Grad Library App </h1>
				<BookList books={this.props.books}></BookList>
			</div>
		);
	}
}

const mapStateToProps = state => ({
	books: state.bookList.books
});
 
// const mapDispatchToProps = dispatch => bindActionCreators(loginActions, dispatch);
 
export default connect(mapStateToProps)(App);
