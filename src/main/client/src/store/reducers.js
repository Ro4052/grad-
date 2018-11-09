import { combineReducers } from "redux";
import bookList from "../dashBoard/bookList/reducer";

const allReducers = combineReducers({
  bookList
});

export default allReducers;
