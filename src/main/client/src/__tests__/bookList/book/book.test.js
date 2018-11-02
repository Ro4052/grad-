import React from 'react';
import Book from "../../../bookList/book/Book";
import { shallow } from "enzyme";

const testBook = {
      title: "Harry Potter",
      id: 0,
      author: "JK Rowling",
      isbn: "1000000000",
      publishDate: "1999"
}

describe('Book Validation Tests', () => {
      test('title matches test data', () => {
            // const store = mockStore(initialState);
            const wrapper = shallow(<Book book={testBook}></Book>)
            // const component = wrapper.dive()
            expect(wrapper.find("h3").text()).toBe(testBook.title)
      })

      test('all fields are populated', () => {
            // const store = mockStore(initialState);
            const wrapper = shallow(<Book book={testBook}></Book>)
            // const component = wrapper.dive()
            expect(wrapper.find("p").text()).toBe(
                  `author: ${testBook.author}, `
                  + `isbn: ${testBook.isbn}, `
                  + `publishDate: ${testBook.publishDate}`
            )
      })

})
