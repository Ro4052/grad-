import moxios from "moxios";
import thunk from "redux-thunk";
import configureStore from "redux-mock-store";

import * as actions from "../../../dashBoard/bookList/reducer";

const middlewares = [thunk];
const mockStore = configureStore(middlewares);

const testBook1 = {
  title: "Harry Potter and the Philosopher's Stone",
  id: 0,
  author: "JK Rowling",
  isbn: "9780747532699",
  publishDate: "1997",
  editState: false,
  popupText: "Loading..."
};

const testBook2 = {
  title: "Lord of the Rings",
  id: 1,
  author: "JRR Tolkien",
  isbn: "9780261103252",
  publishDate: "1954",
  editState: false,
  popupText: "Loading..."
};

describe("BookList reducer tests", () => {
  let initialState;

  beforeEach(() => {
    initialState = {
      bookList: {
        books: [testBook1, testBook2]
      },
      login: {
        loggingIn: false,
        loggedIn: true,
        user: { reservations: [], borrows: [], userDetails: {} }
      }
    };
    moxios.install();
  });

  afterEach(() => {
    moxios.uninstall();
  });

  test("editStateChange updates correct editState", () => {
    const newBooks = [testBook1, { ...testBook2, editState: true }];
    const expectedAction = {
      type: actions.types.GET_BOOKS,
      books: newBooks
    };
    const store = mockStore(initialState);

    store.dispatch(actions.editStateChange(1));
    expect(store.getActions()[0]).toEqual(expectedAction);
  });

  test("dispatches GET_BOOKS action after book is returned", () => {
    const expectedAction = {
      type: actions.types.GET_BOOKS,
      books: [testBook1, testBook2]
    };
    const store = mockStore(initialState);

    store.dispatch(actions.returnBook(0));
    expect(store.getActions()[0]).toEqual(expectedAction);
  });

  test("dispatches GET_BOOKS action after startProcess", () => {
    const expectedAction = {
      type: actions.types.GET_BOOKS,
      books: [testBook1, testBook2]
    };
    const store = mockStore(initialState);

    store.dispatch(actions.startProcess(0));
    expect(store.getActions()[0]).toEqual(expectedAction);
  });

  test("dispatches GET_BOOKS action after cancelProcess", () => {
    const expectedAction = {
      type: actions.types.GET_BOOKS,
      books: [testBook1, testBook2]
    };
    const store = mockStore(initialState);

    store.dispatch(actions.cancelProcess(0));
    expect(store.getActions()[0]).toEqual(expectedAction);
  });
});
