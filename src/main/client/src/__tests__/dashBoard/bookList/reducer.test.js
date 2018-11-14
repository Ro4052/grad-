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
  editState: false
};

const testBook2 = {
  title: "Lord of the Rings",
  id: 1,
  author: "JRR Tolkien",
  isbn: "9780261103252",
  publishDate: "1954",
  editState: false
};

describe("BookList reducer tests", () => {
  let initialState;

  beforeEach(() => {
    initialState = {
      bookList: {
        books: [testBook1, testBook2],
        reservePopText: ""
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

  test("dispatches RESERVE_TEXT loading action", () => {
    const expectedAction = {
      type: actions.types.RESERVE_TEXT,
      text: "Loading..."
    };
    const store = mockStore(initialState);

    store.dispatch(actions.reserveBook(0));
    expect(store.getActions()[0]).toEqual(expectedAction);
  });

  test("dispatches the right RESERVE_TEXT action on successful reservation", done => {
    const expectedAction = {
      type: actions.types.RESERVE_TEXT,
      text: "Reservation successful!"
    };
    const store = mockStore(initialState);

    store.dispatch(actions.reserveBook(0));
    moxios.wait(() => {
      const postRequest = moxios.requests.mostRecent();
      postRequest
        .respondWith({
          status: 200
        })
        .then(() => {
          expect(store.getActions()[1]).toEqual(expectedAction);
          done();
        });
    });
  });

  test("dispatches the right RESERVE_TEXT action on failed reservation", done => {
    const expectedAction = {
      type: actions.types.RESERVE_TEXT,
      text: "Reservation failed"
    };
    const store = mockStore(initialState);

    store.dispatch(actions.reserveBook(0));
    moxios.wait(() => {
      const postRequest = moxios.requests.mostRecent();
      postRequest
        .respondWith({
          status: 404
        })
        .then(() => {
          expect(store.getActions()[1]).toEqual(expectedAction);
          done();
        });
    });
  });
});
