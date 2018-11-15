import axios from "axios";

const types = {
  GET_BOOKS: "bookList/GET_BOOKS",
  EDIT_STATE: "bookList/EDIT_STATE",
  UPDATE_FILTERED_BOOKS: "booklist/UPDATE_FILTERED_BOOKS"
};

const INITIAL_STATE = {
  books: [],
  filteredBooks: []
};

// Actions
const updateBooksAction = books => ({
  type: types.GET_BOOKS,
  books
});

const updateFilteredBooksAction = books => ({
  type: types.UPDATE_FILTERED_BOOKS,
  books
});

// Action Creators
export const getBooks = () => dispatch => {
  axios.get("/api/books").then(res => {
    const books = res.data.map(book => {
      return { ...book, editState: false };
    });
    dispatch(updateBooksAction(books));
    dispatch(updateFilteredBooksAction(books));
  });
};

export const editStateChange = id => (dispatch, getState) => {
  const newBooks = getState().bookList.filteredBooks.map(book => {
    return book.id === id ? { ...book, editState: !book.editState } : book;
  });
  dispatch(updateBooksAction(newBooks));
};

export const updateBook = updatedBook => (dispatch, getState) => {
  axios.put(`/api/books/${updatedBook.id}`, updatedBook);
  const newBooks = getState().bookList.books.map(book => {
    return book.id === updatedBook.id ? updatedBook : book;
  });
  dispatch(updateBooksAction(newBooks));
  // searchBooks(newBooks);
};

export const deleteBook = bookIds => (dispatch, getState) => {
  axios.delete("/api/books", { data: bookIds });
  const newBooks = getState().bookList.books.filter(
    book => !bookIds.includes(book.id.toString())
  );
  dispatch(updateBooksAction(newBooks));
};

export const updateBookLists = bookList => dispatch => {
  searchBooks();
  dispatch(updateBooksAction(bookList));
};

export const searchBooks = (searchString, category) => (dispatch, getState) => {
  const refinedList = getState().bookList.books.filter(book =>
    searchString.length > 0 ? book[category].includes(searchString) : book
  );
  dispatch(updateFilteredBooksAction(refinedList));
};

// Reducers
export default (state = INITIAL_STATE, action) => {
  switch (action.type) {
    case types.GET_BOOKS:
      return { ...state, books: action.books };

    case types.UPDATE_FILTERED_BOOKS:
      return { ...state, filteredBooks: action.books };

    default:
      return state;
  }
};
