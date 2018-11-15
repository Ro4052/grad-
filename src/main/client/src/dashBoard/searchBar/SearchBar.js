import React, { Component } from "react";

class SearchBar extends Component {
  render() {
    return (
      <div>
        <input
          placeholder="Search"
          onChange={this.props.handleChange}
          value={this.props.searchValue}
        />
      </div>
    );
  }
}

export default SearchBar;
