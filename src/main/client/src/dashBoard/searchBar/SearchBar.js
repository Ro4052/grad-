import React, { Component } from "react";
import styles from "./SearchBar.module.css";
import { Icon } from "semantic-ui-react";

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
        {this.props.searchBy !== "publishDate" ? (
          <input
            className={styles.searchBar}
            placeholder="Search"
            onChange={this.props.handleChange}
            value={this.props.searchValue}
          />
        ) : (
          <div className={styles.dateSearchContainer}>
            <label className={styles.dateLabel}>From:</label>
            <input
              placeholder="----"
              className={styles.dateEntry}
              onChange={this.props.handleLowerDateChange}
              value={this.props.lowerDate}
              maxLength="4"
            />
            <label className={styles.dateLabel}>To:</label>
            <input
              placeholder={new Date().getFullYear()}
              className={styles.dateEntry}
              onChange={this.props.handleUpperDateChange}
              value={this.props.upperDate}
              maxLength="4"
            />
          </div>
        )}
      </div>
    );
  }
}

export default SearchBar;
