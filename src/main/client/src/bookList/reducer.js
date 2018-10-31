import * as axios from "axios";

const types = {
    GET_BOOKS: "bookList/GET_BOOKS",
    EDIT_STATE: "bookList/EDIT_STATE"
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
        const books = res.data.map(book => {
            return { ...book, editState: false}
        })
        dispatch(getBooksAction(books))
    });
}

export const editStateChange = id => (dispatch, getState) => {
    console.log("toggling")
    const newBooks = getState().bookList.books.map(book => {
        return book.id === id ? {...book, editState: true} : book
    })
    dispatch(getBooksAction(newBooks))
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