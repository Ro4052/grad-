import React from 'react';
import { shallow } from "enzyme";
import EditBook from '../../../bookList/book/EditBook';
import BookForm from "../../../common/BookForm";


const testBook1 = {
    title: "Harry Potter and the Philosopher's Stone",
    id: 0,
    author: "JK Rowling",
    isbn: "1000000000",
    publishDate: "1997"
}

describe("Testing the edit book component", () => {
    test("check that the edit form renders", () => {
        const wrapper = shallow(<EditBook book={testBook1}></EditBook>);
        expect(wrapper.find(BookForm).exists()).toEqual(true);
    })

})
