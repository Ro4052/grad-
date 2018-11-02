import React from 'react';
import { shallow } from "enzyme";
import EditBook from '../../../bookList/book/EditBook';

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
        expect(wrapper.find(".test").exists()).toEqual(true);
    })

    test("Check that edit state is toggled after form submission", () => {
        const updateBook = jest.fn();
        const wrapper = shallow(<EditBook book={testBook1} updateBook={updateBook}></EditBook>);
        wrapper.find(".test").prop("onSubmit")({preventDefault: () => {}});
        expect(updateBook).toHaveBeenCalled();
    })

})
