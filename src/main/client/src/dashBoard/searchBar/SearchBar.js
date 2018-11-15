import React, { Component } from "react";
import styles from "./SearchBar.module.css";
import { Dropdown, Icon } from "semantic-ui-react";

class SearchBar extends Component {
  render() {
    const options = [
      { key: 1, text: "All", value: 1 },
      { key: 2, text: "Title", value: 2 },
      { key: 3, text: "Author", value: 3 },
      { key: 4, text: "ISBN", value: 4 },
      { key: 5, text: "Publish Date", value: 5 }
    ];

    return (
      <div className={styles.container}>
        <Icon name="search" size="large" />
        {/* <Dropdown text={options.text} options={options} simple item /> */}
        <div className={styles.dropdownContainer}>
          <select className={styles.dropdown}>
            <option value="0">All</option>
            <option value="1">Title</option>
            <option value="2">Author</option>
            <option value="3">ISBN</option>
            <option value="4">Date</option>
          </select>
        </div>
        <input
          className={styles.searchBar}
          placeholder="Search"
          onChange={this.props.handleChange}
          value={this.props.searchValue}
        />
      </div>
    );
  }
}

export default SearchBar;
