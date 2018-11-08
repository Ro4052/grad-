import React from 'react';
import { shallow, mount, dive } from "enzyme";
import AddBook from "../../../dashBoard/addBook/AddBook";
import BookForm from "../../../common/BookForm";

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

    

})