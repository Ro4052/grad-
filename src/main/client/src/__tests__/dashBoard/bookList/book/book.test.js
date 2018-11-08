import React from "react";
import Book from "../../../../dashBoard/bookList/book/Book";
import { shallow } from "enzyme";

const testBook = {
  title: "Harry Potter",
  author: "JK Rowling",
  isbn: "1000000000",
  publishDate: "1999"
};

describe("Book Validation Tests", () => {
  test("title matches test data", () => {
    const wrapper = shallow(<Book book={testBook} />);
    expect(wrapper.find("h3").text()).toBe(testBook.title);
  });

  test("all fields are populated", () => {
    const wrapper = shallow(<Book book={testBook} />);
    expect(wrapper.find("#author").text()).toBe(`author: ${testBook.author}`);
    expect(wrapper.find("#isbn").text()).toBe(`isbn: ${testBook.isbn}`);
    expect(wrapper.find("#publishDate").text()).toBe(
      `publishDate: ${testBook.publishDate}`
    );
  });
});
