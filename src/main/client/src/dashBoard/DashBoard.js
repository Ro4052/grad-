import React, { Component } from "react";
import { bindActionCreators } from "redux";
import { connect } from "react-redux";
import { Button } from "semantic-ui-react";

import * as bookListActions from "./bookList/reducer";
import PageHeader from "../common/pageHeader/PageHeader";
import BookList from "./bookList/BookList";
import AddBook from "./addBook/AddBook";
import DeleteBookModal from "../common/modals/DeleteBookModal";
import SearchBar from "./searchBar/SearchBar";
import styles from "./DashBoard.module.css";

class DashBoard extends Component {
  constructor(props) {
    super(props);
    this.state = {
      deleteMode: false,
      deleteList: [],
      searchString: "",
      lowerDate: "",
      upperDate: new Date().getFullYear(),
      searchBy: "all"
    };
    this.handleCheck = this.handleCheck.bind(this);
    this.toggleSelectMode = this.toggleSelectMode.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.handleSearchByChange = this.handleSearchByChange.bind(this);
    this.handleDateChange = this.handleDateChange.bind(this);
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
      searchString: e.target.value.trimLeft()
    });
  }

  handleDateChange(e) {
    const date = new Date().getFullYear();
    if (/[1234567890]/g.test(e.target.value) || e.target.value === "") {
      if (e.target.value <= date && e.target.value >= 0) {
        this.setState({
          [e.target.id]: e.target.value
        });
      } else if (e.target.value > date) {
        this.setState({
          [e.target.id]: date
        });
      } else if (e.target.value < 0) {
        this.setState({
          [e.target.id]: 0
        });
      }
    }
  }

  handleSearchByChange(e) {
    this.setState({
      searchBy: e.target.value
    });
  }

  render() {
    return (
      <div className={styles.dashBoard}>
        <PageHeader user={this.props.user} loggedIn={this.props.loggedIn} />
        <SearchBar
          handleSearchByChange={this.handleSearchByChange}
          handleChange={this.handleChange}
          handleDateChange={this.handleDateChange}
          searchString={this.state.searchString}
          upperDate={this.state.upperDate}
          lowerDate={this.state.lowerDate}
          searchBy={this.state.searchBy}
        />
        {this.props.loggedIn && (
          <div className={styles.librarianBtns}>
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
        )}
        <BookList
          searchBy={this.state.searchBy}
          searchString={this.state.searchString}
          upperDate={this.state.upperDate}
          lowerDate={this.state.lowerDate}
          deleteMode={this.state.deleteMode}
          handleCheck={this.handleCheck}
        />
        {this.props.loggedIn && <AddBook />}
      </div>
    );
  }
}

const mapStateToProps = state => ({
  user: state.login.user,
  loggedIn: state.login.loggedIn
});

const mapDispatchToProps = dispatch =>
  bindActionCreators({ ...bookListActions }, dispatch);

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DashBoard);
