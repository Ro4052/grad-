import * as axios from "axios";

const types = {
    GET_BOOKS: "bookList/GET_BOOKS"
}

const INITIAL_STATE = {
    books: []
 }

// Actions
const getBooksAction = books => ({
    type: types.GET_BOOKS,
    books
})

// Action Creators
export const getBooks = () => dispatch => {
    axios.get('/api/books').then((res) => {
        dispatch(getBooksAction(res.data))
    });
}

// Reducers
export default (state = INITIAL_STATE, action) => {
    switch (action.type) {
        case types.GET_BOOKS:
            return { ...state, books: action.books }

        default:
            return state
    }
}