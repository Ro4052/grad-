import * as axios from "axios";
import { checkAllBooks } from "../dashBoard/bookList/reducer";

import history from "../history";

const types = {
  CHECKING_LOGIN: "login/CHECKING_LOGIN",
  LOGGED_IN: "login/LOGGED_IN",
  FAILED_LOGIN: "login/FAILED_LOGIN",
  LOGIN_USER: "login/LOGIN_USER",
  LOADING: "login/LOADING",
  LOGOUT: "login/LOGOUT"
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

    default:
      return state;
  }
};
