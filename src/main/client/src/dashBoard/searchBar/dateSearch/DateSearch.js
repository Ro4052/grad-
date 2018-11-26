import React, { Component } from "react";
import styles from "../SearchBar.module.css";

export default class DateSearch extends Component {
  render() {
    const date = new Date().getFullYear();
    return (
      <input
        id={this.props.id}
        placeholder={this.props.placeholder || date}
        onChange={this.props.handleDateChange}
        value={this.props.value}
        type="number"
        className={styles.dateEntry}
        max={date}
        min={0}
      />
    );
  }
}
