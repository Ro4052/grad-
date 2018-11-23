import axios from "axios";

export const types = {
  GET_BOOKS: "bookList/GET_BOOKS",
  POPUP_TEXT: "bookList/POPUP_TEXT"
};

const INITIAL_STATE = {
  books: [],
  popupText: ""
};

// Actions
const getBooksAction = books => ({
  type: types.GET_BOOKS,
  books
});

const popupText = text => ({
  type: types.POPUP_TEXT,
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

export const bookIsAvailable = id => (dispatch, getState) => {
  console.log("in here");
  const newBooks = getState().bookList.books.map(book => {
    console.log(book);
    return book.id === id ? { ...book, isAvailable: true } : book;
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
  dispatch(popupText("Loading..."));
  axios
    .post(`/api/reserve/${bookId}`)
    .then(() => {
      // Success popup text
      dispatch(popupText("Reservation successful!"));
    })
    .catch(() => {
      // Failure popup text
      dispatch(popupText("Reservation failed"));
    });
};

export const borrowBook = bookId => dispatch => {
  dispatch(popupText("Loading..."));
  axios
    .post(`/api/borrow/${bookId}`)
    .then(() => {
      dispatch(popupText("Book successfully borrowed!"));
    })
    .catch(() => {
      dispatch(popupText("Book cannot be borrowed at this time"));
    });
};

export const checkBook = bookId => dispatch => {
  dispatch(popupText("Loading..."));
  axios
    .get(`/api/borrow/check/${bookId}`)
    .then(res => {
      if (res.data) {
        axios.get(`/api/reserve/check/${bookId}`).then(res => {
          dispatch(popupText(`Number of reservations: ${res.data}`));
        });
      } else {
        dispatch(popupText("Available"));
        dispatch(bookIsAvailable(bookId));
      }
    })
    .catch(() => {
      dispatch(popupText("Something went wrong"));
    });
};

// Reducers
export default (state = INITIAL_STATE, action) => {
  switch (action.type) {
    case types.GET_BOOKS:
      return { ...state, books: action.books };
    case types.POPUP_TEXT:
      return { ...state, popupText: action.text };
    default:
      return state;
  }
};
