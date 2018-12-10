import axios from "axios";

import {
  addBorrow,
  removeBorrow,
  addReservation,
  removeReservation
} from "../../login/reducer";

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
export const getBooks = () => (dispatch, getState) => {
  axios.get("/api/books").then(res => {
    const books = res.data.map(book => {
      return {
        ...book,
        editState: false
      };
    });
    dispatch(getBooksAction(books));
    dispatch(checkAllBooks(getState().login.user));
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
    return book.id === updatedBook.id
      ? {
          ...updatedBook,
          editState: false,
          borrowId: book.borrowId,
          reservationId: book.reservationId,
          state: book.state,
          collectState: book.collectState
        }
      : book;
  });
  dispatch(getBooksAction(newBooks));
};

export const deleteBook = bookIds => (dispatch, getState) => {
  axios.delete("/api/books", { data: bookIds });
  const newBooks = getState().bookList.books.filter(
    book => !bookIds.includes(book.id)
  );
  dispatch(getBooksAction(newBooks));
};

export const reserveBook = book => (dispatch, getState) => {
  const bookId = book.id;
  axios
    .post(`/api/reserve/${bookId}`)
    .then(res => {
      const newBooks = getState().bookList.books.map(eachBook => {
        if (eachBook.id === book.id) {
          eachBook.borrowId = null;
          eachBook.reservationId = res.data.id;
          eachBook.state = "Reserver - Cancel Not Started";
          eachBook.popupText = "Cancel your Reservation";
          dispatch(addReservation(res.data, book.id));
        }
        return eachBook;
      });
      dispatch(getBooksAction(newBooks));
    })
    .catch(() => {
      dispatch(setBookState(bookId, "Something went wrong", null));
    });
};

export const borrowBook = book => (dispatch, getState) => {
  const bookId = book.id;
  axios
    .post(`/api/borrow/${bookId}`)
    .then(res => {
      const newBooks = getState().bookList.books.map(eachBook => {
        if (eachBook.id === book.id) {
          eachBook.borrowId = res.data.id;
          eachBook.reservationId = null;
          eachBook.state = "Borrower - Return Not Started";
          dispatch(addBorrow(res.data, book.id));
        }
        return eachBook;
      });
      dispatch(getBooksAction(newBooks));
    })
    .catch(() => {
      dispatch(setBookState(bookId, "Something went wrong", null));
    });
};

export const collectBook = book => (dispatch, getState) => {
  const bookId = book.id;
  axios
    .post(`/api/borrow/collect/${bookId}`)
    .then(res => {
      const newBooks = getState().bookList.books.map(eachBook => {
        if (eachBook.id === book.id) {
          dispatch(removeReservation(eachBook.reservationId));
          eachBook.borrowId = res.data.id;
          eachBook.reservationId = null;
          eachBook.state = "Borrower - Return Not Started";
          eachBook.collectBook = "No State";
          dispatch(addBorrow(res.data, book.id));
        }
        return eachBook;
      });
      dispatch(getBooksAction(newBooks));
    })
    .catch(() => {
      dispatch(setBookState(bookId, "Something went wrong", null));
    });
};

export const checkBook = book => (dispatch, getState) => {
  const bookId = book.id;
  let state;
  axios
    .get(`/api/borrow/check/${bookId}`)
    .then(res => {
      if (res.data) {
        axios.get(`/api/reserve/check/${bookId}`).then(res => {
          state = "Available to Reserve";
          dispatch(setBookState(bookId, state, res.data));
        });
      } else {
        state = "Available to Borrow";
        dispatch(setBookState(bookId, state, 0));
      }
    })
    .catch(() => {
      state = "Something went wrong";
      dispatch(setBookState(bookId, state, null));
    });
};

export const setBookState = (id, state, queueLength) => (
  dispatch,
  getState
) => {
  const newBooks = getState().bookList.books.map(book => {
    return book.id === id
      ? { ...book, state: state, queueLength: queueLength }
      : book;
  });
  dispatch(getBooksAction(newBooks));
};

export const checkAllBooks = user => (dispatch, getState) => {
  let state, reservationId, borrowId;
  const newBooks = getState().bookList.books.map(book => {
    borrowId = null;
    reservationId = null;
    state = "No State";
    user.reservations.forEach(reservation => {
      if (reservation.bookId === book.id) {
        borrowId = null;
        reservationId = reservation.id;
        state = "Reserver - Cancel Not Started";
      }
    });
    user.borrows.forEach(borrow => {
      if (borrow.bookId === book.id && borrow.active) {
        borrowId = borrow.id;
        reservationId = null;
        state = "Borrower - Return Not Started";
      }
    });
    book.borrowId = borrowId;
    book.reservationId = reservationId;
    book.state = state;
    book.collectState = "Default State";
    return book;
  });
  dispatch(getBooksAction(newBooks));
};

const cancelHelper = (book, books) => {
  const newBooks = books.map(eachBook => {
    if (eachBook.id === book.id) {
      eachBook.borrowId = null;
      eachBook.reservationId = null;
      eachBook.state = "No State";
    }
    return eachBook;
  });
  return newBooks;
};

export const returnBook = book => (dispatch, getState) => {
  axios.put(`/api/borrow/return/${book.borrowId}`);
  const borrowToEnd = book.borrowId;
  const newBooks = cancelHelper(book, getState().bookList.books);
  dispatch(getBooksAction(newBooks));
  dispatch(removeBorrow(borrowToEnd));
};

export const cancelReservation = book => (dispatch, getState) => {
  axios.delete(`/api/reserve/${book.reservationId}`);
  const resToCancel = book.reservationId;
  const newBooks = cancelHelper(book, getState().bookList.books);
  dispatch(getBooksAction(newBooks));
  dispatch(removeReservation(resToCancel));
};

export const startProcess = book => (dispatch, getState) => {
  let state;
  switch (book.state) {
    case "Borrower - Return Not Started":
      state = "Borrower - Return Started";
      break;
    case "Reserver - Cancel Not Started":
      state = "Reserver - Cancel Started";
      break;
    default:
      state = "No State";
      break;
  }
  const newBooks = getState().bookList.books.map(eachBook => {
    if (eachBook.id === book.id) {
      eachBook.state = state;
    }
    return eachBook;
  });
  dispatch(getBooksAction(newBooks));
};

export const cancelProcess = book => (dispatch, getState) => {
  let state;
  switch (book.state) {
    case "Borrower - Return Started":
      state = "Borrower - Return Not Started";
      break;
    case "Reserver - Cancel Started":
      state = "Reserver - Cancel Not Started";
      break;
    default:
      state = "No State";
      break;
  }
  const newBooks = getState().bookList.books.map(eachBook => {
    if (eachBook.id === book.id) {
      eachBook.state = state;
    }
    return eachBook;
  });
  dispatch(getBooksAction(newBooks));
};

export const startCollection = book => (dispatch, getState) => {
  const newBooks = getState().bookList.books.map(eachBook => {
    if (eachBook.id === book.id) {
      eachBook.collectState = "Collector - Collection Started";
    }
    return eachBook;
  });
  dispatch(getBooksAction(newBooks));
};

export const cancelCollection = book => (dispatch, getState) => {
  const newBooks = getState().bookList.books.map(eachBook => {
    if (eachBook.id === book.id) {
      eachBook.collectState = "Collector - Collection Not Started";
    }
    return eachBook;
  });
  dispatch(getBooksAction(newBooks));
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
