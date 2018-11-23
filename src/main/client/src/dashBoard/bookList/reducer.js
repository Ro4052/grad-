import axios from "axios";

export const types = {
  GET_BOOKS: "bookList/GET_BOOKS"
};

const INITIAL_STATE = {
  books: []
};

// Actions
const getBooksAction = books => ({
  type: types.GET_BOOKS,
  books
});

// Action Creators
export const getBooks = () => dispatch => {
  axios.get("/api/books").then(res => {
    const books = res.data.map(book => {
      return {
        ...book,
        editState: false,
        popupText: "click to check availability",
        availabilityChecked: false,
        isAvailable: undefined
      };
    });
    dispatch(getBooksAction(books));
  });
};

export const editStateChange = id => (dispatch, getState) => {
  console.log("toggling");
  const newBooks = getState().bookList.books.map(book => {
    return book.id === id ? { ...book, editState: !book.editState } : book;
  });
  dispatch(getBooksAction(newBooks));
};

export const isBookAvailable = (id, bool) => (dispatch, getState) => {
  const newBooks = getState().bookList.books.map(book => {
    return book.id === id ? { ...book, isAvailable: bool } : book;
  });
  dispatch(getBooksAction(newBooks));
};

export const popupText = (text, id) => (dispatch, getState) => {
  const newBooks = getState().bookList.books.map(book => {
    return book.id === id ? { ...book, popupText: text } : book;
  });
  dispatch(getBooksAction(newBooks));
};

export const updateBook = updatedBook => (dispatch, getState) => {
  console.log(updatedBook);
  axios.put(`/api/books/${updatedBook.id}`, updatedBook);
  const newBooks = getState().bookList.books.map(book => {
    console.log(book);
    return book.id === updatedBook.id
      ? {
          ...updatedBook,
          popupText: book.popupText,
          availabilityChecked: book.availabilityChecked,
          isAvailable: book.isAvailable,
          editState: false
        }
      : book;
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
  dispatch(popupText("Loading...", bookId));
  axios
    .post(`/api/reserve/${bookId}`)
    .then(() => {
      // Success popup text
      dispatch(popupText("Reservation successful!", bookId));
    })
    .catch(() => {
      // Failure popup text
      dispatch(popupText("Reservation failed", bookId));
    });
};

export const borrowBook = bookId => dispatch => {
  dispatch(popupText("Loading...", bookId));
  axios
    .post(`/api/borrow/${bookId}`)
    .then(() => {
      dispatch(popupText("Book successfully borrowed!", bookId));
    })
    .catch(() => {
      dispatch(popupText("Book cannot be borrowed at this time", bookId));
    });
};

export const checkBook = bookId => dispatch => {
  dispatch(popupText("Loading...", bookId));
  axios
    .get(`/api/borrow/check/${bookId}`)
    .then(res => {
      if (res.data) {
        axios.get(`/api/reserve/check/${bookId}`).then(res => {
          dispatch(
            popupText(`Number of reservations: ${res.data}`, bookId, false)
          );
          dispatch(isBookAvailable(bookId, false));
        });
      } else {
        dispatch(popupText("Available", bookId, true));
        dispatch(isBookAvailable(bookId, true));
      }
    })
    .catch(() => {
      dispatch(popupText("Something went wrong", bookId));
    });
};

// Reducers
export default (state = INITIAL_STATE, action) => {
  switch (action.type) {
    case types.GET_BOOKS:
      return { ...state, books: action.books };
    default:
      return state;
  }
};
