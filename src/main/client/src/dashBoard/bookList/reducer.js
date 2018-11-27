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
  const newBooks = getState().bookList.books.map(book => {
    return book.id === id ? { ...book, editState: !book.editState } : book;
  });
  dispatch(getBooksAction(newBooks));
};

export const isBookAvailable = (id, bool) => (dispatch, getState) => {
  const newBooks = getState().bookList.books.map(book => {
    return book.id === id
      ? { ...book, isAvailable: bool, availabilityChecked: true }
      : book;
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
  axios.put(`/api/books/${updatedBook.id}`, updatedBook);
  const newBooks = getState().bookList.books.map(book => {
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
  //  need to set the "role" state of the userStatus for the bookId to "Reserver"
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
  // This needs to change, needs to set the "role" state of the userStatus for the bookId to "Borrower"
  dispatch(isBookAvailable(bookId, false));
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

export const checkAllBooks = () => (dispatch, getState) => {
  console.log("HELOOOOOO");
  let role, reservationId, borrowId;
  const user = getState().login.user;
  // console log out the user
  const newBooks = getState().bookList.books.forEach(book => {
    if (user.reservations) {
      this.props.user.reservations.forEach(reservation => {
        if (reservation.bookId === book.id) {
          borrowId = null;
          reservationId = reservation.id;
          role = "Reserver";
          dispatch(popupText("Cancel Reservation", book.id));
        }
      });
    } else if (user.borrows) {
      this.props.user.borrows.forEach(borrow => {
        if (borrow.bookId === book.id) {
          borrowId = borrow.id;
          reservationId = null;
          role = "Borrower";
          dispatch(popupText("Return Book", book.id));
        }
      });
    } else {
      borrowId = null;
      reservationId = null;
      role = "Nothing";
    }
    book.borrowId = borrowId;
    book.reservationId = reservationId;
    book.role = role;
  });
  // const newBooks = getState().bookList.books.map(book => {
  //   return bookId === book.id
  //     ? { ...book, role: role, reservationId, borrowId }
  //     : book;
  // });
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
