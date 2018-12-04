import { createStore, applyMiddleware, compose } from "redux";
import thunk from "redux-thunk";
import allReducers from "./reducers";
import { getBooks } from "../dashBoard/bookList/reducer";
import { checkLogin } from "../login/reducer";

const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;
const store = createStore(
  allReducers,
  composeEnhancers(applyMiddleware(thunk))
);
const dispatch = store.dispatch;
dispatch(getBooks());
dispatch(checkLogin());

export default store;
