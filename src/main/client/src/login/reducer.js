import * as axios from "axios";
import { checkAllBooks } from "../dashBoard/bookList/reducer";

import history from "../history";

const types = {
  CHECKING_LOGIN: "login/CHECKING_LOGIN",
  FAILED_LOGIN: "login/FAILED_LOGIN",
  LOGIN_USER: "login/LOGIN_USER",
  LOGGED_IN: "login/LOGGED_IN",
  LOGOUT: "login/LOGOUT",
  LOADING: "login/LOADING",
  ADD_BORROW: "login/ADD_BORROW",
  REMOVE_BORROW: "login/REMOVE_BORROW",
  ADD_RESERVATION: "login/ADD_RESERVATION",
  REMOVE_RESERVATION: "login/REMOVE_RESERVATION"
};

const INITIAL_STATE = {
  loggingIn: false,
  loggedIn: false,
  user: { reservations: [], borrows: [] }
};

const checkingLogin = checking => ({
  type: types.CHECKING_LOGIN,
  checking
});

const loggedIn = userData => ({
  type: types.LOGGED_IN,
  userData
});

const loginUserAction = {
  type: types.LOGIN_USER
};

const logOutAction = () => ({
  type: types.LOGOUT
});

const addBorrowAction = newBorrow => ({
  type: types.ADD_BORROW,
  newBorrow
});

const removeBorrowAction = newBorrowList => ({
  type: types.REMOVE_BORROW,
  newBorrowList
});

const addReservationAction = newReservation => ({
  type: types.ADD_RESERVATION,
  newReservation
});

const removeReservationAction = newResList => ({
  type: types.REMOVE_RESERVATION,
  newResList
});

export const checkLogin = () => dispatch => {
  dispatch(checkingLogin(true));
  axios
    .get("/api/user")
    .then(userResult => {
      dispatch(loggedIn(userResult.data));
      dispatch(checkAllBooks(userResult.data));
    })
    .catch(() => {
      history.replace("/dashboard");
      dispatch(checkingLogin(false));
    });
};

export const loginUser = () => dispatch => {
  window.location = process.env.REACT_APP_LOGIN;
  dispatch(loginUserAction);
};

export const logOut = () => dispatch => {
  dispatch(logOutAction());
  axios.post("/logout");
};

export const addBorrow = (borrow, bookId) => (dispatch, getState) => {
  const newBorrow = {
    id: borrow.id,
    bookId: bookId,
    userId: getState().login.user.userDetails.userId,
    borrowDate: borrow.borrowDate,
    returnDate: borrow.returnDate,
    active: true
  };
  dispatch(addBorrowAction(newBorrow));
};

export const removeBorrow = borrowId => (dispatch, getState) => {
  const newBorrowList = [...getState().login.user.borrows];
  let inactiveBorrow = newBorrowList.find(borrow => borrow.id === borrowId);
  if (inactiveBorrow) {
    inactiveBorrow.active = false;
  }
  dispatch(removeBorrowAction(newBorrowList));
};

export const addReservation = (reservation, bookId) => (dispatch, getState) => {
  const newReservation = {
    id: reservation.id,
    bookId: bookId,
    userId: getState().login.user.userDetails.userId,
    queuePosition: reservation.queuePosition
  };
  dispatch(addReservationAction(newReservation));
};

export const removeReservation = resId => (dispatch, getState) => {
  const newResList = getState().login.user.reservations.filter(res => {
    return res.id !== resId;
  });
  dispatch(removeReservationAction(newResList));
};

export default (state = INITIAL_STATE, action) => {
  switch (action.type) {
    case types.CHECKING_LOGIN:
      return { ...state, loggingIn: action.checking };

    case types.LOGGED_IN:
      return {
        ...state,
        loggingIn: false,
        loggedIn: true,
        user: action.userData
      };

    case types.LOGIN_USER:
      return { ...state, loggingIn: true };

    case types.LOGOUT:
      return { ...state, loggingIn: false, loggedIn: false, user: {} };

    case types.ADD_BORROW:
      return {
        ...state,
        user: {
          ...state.user,
          borrows: [...state.user.borrows, action.newBorrow]
        }
      };

    case types.REMOVE_BORROW:
      return {
        ...state,
        user: { ...state.user, borrows: action.newBorrowList }
      };

    case types.ADD_RESERVATION:
      return {
        ...state,
        user: {
          ...state.user,
          reservations: [...state.user.reservations, action.newReservation]
        }
      };

    case types.REMOVE_RESERVATION:
      return {
        ...state,
        user: { ...state.user, reservations: action.newResList }
      };

    default:
      return state;
  }
};
