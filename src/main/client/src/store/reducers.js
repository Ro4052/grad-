import { combineReducers } from "redux";
import bookList from "../dashBoard/bookList/reducer";
import login from "../login/reducer";

const allReducers = combineReducers({
  bookList,
  login
});

export default allReducers;
