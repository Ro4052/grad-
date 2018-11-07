import React from "react";
import { shallow } from "enzyme";
import BookList from "../../bookList/BookList";
import configureStore from "redux-mock-store";

const mockStore = configureStore();

const testBook1 = {
  title: "Harry Potter and the Philosopher's Stone",
  id: 0,
  author: "JK Rowling",
  isbn: "9780747532699",
  publishDate: "1997"
};
const testBook2 = {
  title: "Lord of the Rings",
  id: 1,
  author: "JRR Tolkien",
  isbn: "9780261103252",
  publishDate: "1954"
};

const initialState = { bookList: { books: [] } };
let store;

const createStore = books => {
  const state = initialState;
  state.bookList.books = books;
  return (store = mockStore(state));
};

describe("Testing the book list", () => {
  test("book list is empty when there is no books", () => {
    const books = [];
    store = createStore(books);
    const wrapper = shallow(<BookList books={books} store={store} />);
    const component = wrapper.dive();
    expect(component.find("ul").children().length).toEqual(0);
  });

  test("book list is length 1 when 1 book is passed in", () => {
    const books = [testBook1];
    store = createStore(books);
    const wrapper = shallow(<BookList store={store} />);
    const component = wrapper.dive();
    expect(component.find("ul").children().length).toEqual(1);
  });

  test("book list is length 2 when 2 books are passed in", () => {
    const books = [testBook1, testBook2];
    store = createStore(books);
    const wrapper = shallow(<BookList books={books} store={store} />);
    const component = wrapper.dive();
    expect(component.find("ul").children().length).toEqual(2);
  });
});
