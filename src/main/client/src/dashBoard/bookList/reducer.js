import axios from "axios";

export const types = {
  GET_BOOKS: "bookList/GET_BOOKS",
  RESERVE_TEXT: "bookList/RESERVE_TEXT",
  CHECK_TEXT: "booklist/CHECK_TEXT"
};

const INITIAL_STATE = {
  books: [],
  reservePopText: ""
};

// Actions
const getBooksAction = books => ({
  type: types.GET_BOOKS,
  books
});

const reserveText = text => ({
  type: types.RESERVE_TEXT,
  text
});

const checkBookText = text => ({
  type: types.CHECK_TEXT,
  text
});

// Action Creators
export const getBooks = () => dispatch => {
  axios.get("/api/books").then(res => {
    const books = res.data.map(book => {
      return { ...book, editState: false };
    });
    dispatch(getBooksAction(books));
  });
};

export const editStateChange = id => (dispatch, getState) => {
  const newBooks = getState().bookList.books.map(book => {
    return book.id === id ? { ...book, editState: !book.editState } : book;
  });
  dispatch(getBooksAction(newBooks));
};

export const updateBook = updatedBook => (dispatch, getState) => {
  axios.put(`/api/books/${updatedBook.id}`, updatedBook);
  const newBooks = getState().bookList.books.map(book => {
    return book.id === updatedBook.id ? updatedBook : book;
  });
  dispatch(getBooksAction(newBooks));
};

export const deleteBook = bookIds => (dispatch, getState) => {
  axios.delete("/api/books", { data: bookIds });
  const newBooks = getState().bookList.books.filter(
    book => !bookIds.includes(book.id.toString())
  );
  dispatch(getBooksAction(newBooks));
};

export const reserveBook = bookId => dispatch => {
  // Reset popup text
  dispatch(reserveText("Loading..."));
  axios
    .post(`/api/reserve/${bookId}`)
    .then(() => {
      // Success popup text
      dispatch(reserveText("Reservation successful!"));
    })
    .catch(() => {
      // Failure popup text
      dispatch(reserveText("Reservation failed"));
    });
};

export const checkBook = bookId => dispatch => {
  dispatch(checkBookText("Loading..."));
  axios
    .get(`/api/reserve/check/${bookId}`)
    .then(res => {
      res.data
        ? dispatch(checkBookText(`There are ${res.data} reservations`))
        : dispatch(checkBookText("Available"));
    })
    .catch(() => {
      dispatch(checkBookText("Something went wrong"));
    });
};

// Reducers
export default (state = INITIAL_STATE, action) => {
  switch (action.type) {
    case types.GET_BOOKS:
      return { ...state, books: action.books };
    case types.RESERVE_TEXT:
      return { ...state, reservePopText: action.text };
    case types.CHECK_TEXT:
      return { ...state, checkBookPopText: action.text };
    default:
      return state;
  }
};
