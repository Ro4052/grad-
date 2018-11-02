import React from 'react';
import ReactDOM from 'react-dom';
import BookForm from '../../common/BookForm';
import { shallow } from "enzyme";

let wrapper;
const newBook = {
    id: "",
    isbn: "",
    title: "",
    author: "",
publishDate: ""}

describe('Testing the book form', () => {
    beforeEach(() => {
    wrapper = shallow(<BookForm book={newBook}
        buttonText = "Add Book" > </BookForm>);
    });

	test('Renders without crashing', () => {
        expect(wrapper.find("form").exists()).toEqual(true);
    });
    test("All Book input fields loaded", () => {
        expect(wrapper.find("#isbn").text()).toBe("")
        expect(wrapper.find("#title").text()).toBe("")
        expect(wrapper.find("#author").text()).toBe("")
        expect(wrapper.find("#publishDate").text()).toBe("")
        expect(wrapper.find("#submitButton").text()).toBe("Add Book")
    })

    test("Leading white spaces not allowed", () => {
        wrapper.find("#publishDate").simulate("change", {target: {value: "1"} })
        // expect(component.find("#isbn").text()).toBe("1")
        // expect(component.find("#title").text()).toBe("2")
        // expect(component.find("#author").text()).toBe("3")
        expect(component.find("#publishDate").text()).toBe("4")
    })

    // test('all fields are populated', () => {
    //     expect(wrapper.find("p").text()).toBe(
    //             `id: ${testBook.id}, `
    //             + `author: ${testBook.author}, `
    //             + `isbn: ${testBook.isbn}, `
    //             + `publishDate: ${testBook.publishDate}`
    //     )
    // })
})


