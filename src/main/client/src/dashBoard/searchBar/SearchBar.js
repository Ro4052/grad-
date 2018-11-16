import React, { Component } from "react";
import styles from "./SearchBar.module.css";
import { Dropdown, Icon } from "semantic-ui-react";

class SearchBar extends Component {
  render() {
    return (
      <div className={styles.container}>
        <Icon name="search" size="large" />
        <div className={styles.dropdownContainer}>
          <select
            className={styles.dropdown}
            onChange={this.props.handleSearchByChange}
          >
            <option value="all">All</option>
            <option value="title">Title</option>
            <option value="author">Author</option>
            <option value="isbn">ISBN</option>
            <option value="publishDate">Date</option>
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
