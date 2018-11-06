import React, { Component } from 'react';
import styles from './App.module.css';
import DashBoard from "./dashBoard/DashBoard";

export default class App extends Component {
	render() {
		return (
			<div className={styles.container}>
				<DashBoard />
			</div>
		);
	}
}
