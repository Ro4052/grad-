import thunk from "redux-thunk";
import configureStore from "redux-mock-store";

import * as actions from "../../../dashBoard/bookList/reducer";

const middlewares = [thunk];
const mockStore = configureStore(middlewares);

describe("BookList reducer tests", () => {
  let initialState;

  beforeEach(() => {
    initialState = {
      books: [
        {
          title: "Harry Potter and the Philosopher's Stone",
          id: 0,
          author: "JK Rowling",
          isbn: "9780747532699",
          publishDate: "1997"
        }
      ],
      reservePopText: ""
    };
  });

  test("Dispatches RESERVE_TEXT loading action", () => {
    const expectedActions = [
      {
        type: actions.types.RESERVE_TEXT,
        text: "Loading..."
      }
    ];
    const store = mockStore(initialState);

    store.dispatch(actions.reserveBook(0));
    expect(store.getActions()).toEqual(expectedActions);
  });
});
