import React from 'react';
import { shallow, mount, dive } from "enzyme";
import AddBook from "../../addBook/AddBook";
import BookForm from "../../common/BookForm";

describe('Add Book Tests', () => {
    let wrapper;
    const state = {
        isbn: "",
        title: "",
        author: "",
        publishDate: "",
        buttonText: ""
    }
  beforeEach(() => {
    wrapper = shallow(<AddBook/>);
  });
    test("Renders the component", () => {
		expect(wrapper.find(BookForm).exists()).toEqual(true);
    })    

    // test("All Book input fields loaded", () => {
    //     const component = wrapper.find(BookForm).dive()
    //     expect(component.find("#isbn").text()).toBe("")
    //     expect(component.find("#title").text()).toBe("")
    //     expect(component.find("#author").text()).toBe("")
    //     expect(component.find("#publishDate").text()).toBe("")
    //     expect(component.find("#submitButton").text()).toBe("")
    // })

    // test("Leading white spaces not allowed", () => {
    //     const component = wrapper.find(BookForm).dive()
    //     // wrapper.find('#publishDate').instance().value = 'Test';
    //     // component.find("#publishDate").simulate("change", {target: {value: "1"} })
    //     expect(component.find("#isbn").text()).toBe("1")
    //     expect(component.find("#title").text()).toBe("2")
    //     expect(component.find("#author").text()).toBe("3")
    //     expect(component.find("#publishDate").text()).toBe("4")
    // })

    // test('all fields are populated', () => {
    //     expect(wrapper.find("p").text()).toBe(
    //             `id: ${testBook.id}, `
    //             + `author: ${testBook.author}, `
    //             + `isbn: ${testBook.isbn}, `
    //             + `publishDate: ${testBook.publishDate}`
    //     )
    // })

})