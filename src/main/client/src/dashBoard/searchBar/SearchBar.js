import React, { Component } from "react";
import { bindActionCreators } from "redux";
import { connect } from "react-redux";
import * as bookListActions from "../bookList/reducer";

class SearchBar extends Component {
  constructor(props) {
    super(props);
    this.state = {
      value: ""
    };
    this.handleChange = this.handleChange.bind(this);
  }
  handleChange(e) {
    this.setState({ value: e.target.value });
    this.props.searchBooks(e.target.value, "title");
  }

  render() {
    const { value } = this.state;
    return (
      <div>
        <input
          placeholder="Search"
          onChange={this.handleChange}
          value={value}
        />
      </div>
    );
  }
}

const mapDispatchToProps = dispatch =>
  bindActionCreators({ ...bookListActions }, dispatch);

export default connect(
  null,
  mapDispatchToProps
)(SearchBar);
