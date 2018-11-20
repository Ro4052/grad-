import React, { Component } from "react";
import styles from "./SearchBar.module.css";
import { Icon } from "semantic-ui-react";
import DateSearch from "./dateSearch/DateSearch";

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
            <DateSearch
              handleDateChange={this.props.handleDateChange}
              id="lowerDate"
              value={this.props.lowerDate}
              placeholder="----"
            />
            <label className={styles.dateLabel}>To:</label>
            <DateSearch
              handleDateChange={this.props.handleDateChange}
              id="upperDate"
              value={this.props.upperDate}
            />
          </div>
        )}
      </div>
    );
  }
}

export default SearchBar;
