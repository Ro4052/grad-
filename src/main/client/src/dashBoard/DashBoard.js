import React, { Component } from "react";
import styles from "./DashBoard.module.css";
import BookList from "./bookList/BookList";
import AddBook from "./addBook/AddBook";
import DeleteBookModal from "../common/modals/DeleteBookModal";
import { connect } from "react-redux";
import * as bookListActions from "./bookList/reducer";
import { bindActionCreators } from "redux";
import { Button } from "semantic-ui-react";
import scottLogicLogo from "../common/SL_primary_AW_POS_LO_RGB.jpg";
import SearchBar from "./searchBar/SearchBar";

class DashBoard extends Component {
  constructor(props) {
    super(props);
    this.state = {
      deleteMode: false,
      deleteList: [],
      searchString: "",
      searchBy: "all"
    };
    this.handleCheck = this.handleCheck.bind(this);
    this.toggleSelectMode = this.toggleSelectMode.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.handleSearchByChange = this.handleSearchByChange.bind(this);
  }

  handleCheck(event) {
    let newDeleteList = [...this.state.deleteList];
    event.target.checked
      ? newDeleteList.push(event.target.value)
      : (newDeleteList = newDeleteList.filter(id => id !== event.target.value));
    this.setState({ deleteList: newDeleteList });
  }

  toggleSelectMode() {
    this.setState({ deleteMode: !this.state.deleteMode });
    this.setState({ deleteList: [] });
  }

  handleChange(e) {
    this.setState({
      searchString: e.target.value
    });
  }

  handleSearchByChange(e) {
    console.log(e.target.value);
    this.setState({
      searchBy: e.target.value
    });
  }

  render() {
    return (
      <div className={styles.dashBoard}>
        <div className={styles.navBar}>
          <img
            src={scottLogicLogo}
            alt="Scott Logic Logo"
            className={styles.logo}
          />
          <h1 className={styles.pageHeader}> Grad Library App </h1>
          <div className={styles.navBtns}>
            <Button
              className={styles.selectBookBtn}
              size="small"
              onClick={this.toggleSelectMode}
            >
              Select Books
            </Button>
            <DeleteBookModal
              deleteMode={this.state.deleteMode}
              toggleSelectMode={this.toggleSelectMode}
              deleteList={this.state.deleteList}
              deleteBook={this.props.deleteBook}
            />
          </div>
        </div>
        <SearchBar
          // searchBySelection={this.props.searchBySelection}
          handleSearchByChange={this.handleSearchByChange}
          handleChange={this.handleChange}
          searchValue={this.state.searchValue}
        />
        <BookList
          searchBy={this.state.searchBy}
          searchString={this.state.searchString}
          deleteMode={this.state.deleteMode}
          handleCheck={this.handleCheck}
        />
        <AddBook />
      </div>
    );
  }
}

const mapDispatchToProps = dispatch =>
  bindActionCreators({ ...bookListActions }, dispatch);

export default connect(
  null,
  mapDispatchToProps
)(DashBoard);
