import { combineReducers } from "redux";
import bookList from "../bookList/reducer";

const allReducers = combineReducers({
   bookList
});

export default allReducers;