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
        wrapper.find("#isbn").simulate("change", {target: {id: "isbn", value:  "     2321"}})
        wrapper.find("#title").simulate("change", {target: {id: "title", value:  "     Title1"}})
        wrapper.find("#author").simulate("change", {target: {id: "author", value:  " Author1"}})
        wrapper.find("#publishDate").simulate("change", {target: {id: "publishDate", value:  "     1"}})
        expect(wrapper.state("isbn")).toEqual("2321")
        expect(wrapper.state("title")).toEqual("Title1")
        expect(wrapper.state("author")).toEqual("Author1")
        expect(wrapper.state("publishDate")).toEqual("1")
    })

    test("Test publish Date is sliced to 4 digits", () => {
        wrapper.find("#publishDate").simulate("change", {target: {id: "publishDate", value:  "123111"}})
        expect(wrapper.state("publishDate")).toEqual("1231")
    })


})
