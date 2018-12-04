import React from "react";
import { shallow } from "enzyme";
import { BookList } from "../../../dashBoard/bookList/BookList";

const testBook1 = {
  title: "Harry Potter and the Philosopher's Stone",
  id: 0,
  author: "JK Rowling",
  isbn: "9780747532699",
  publishDate: "1997"
};
const testBook2 = {
  title: "Lord of the Rings",
  id: 1,
  author: "JRR Tolkien",
  isbn: "9780261103252",
  publishDate: "1954"
};

const searchString = "";

describe("Testing the book list", () => {
  test("book list is empty when there is no books", () => {
    const books = [];
    const wrapper = shallow(
      <BookList books={books} searchString={searchString} />
    );
    expect(wrapper.find("ul").children().length).toEqual(0);
  });

  test("book list is length 1 when 1 book is passed in", () => {
    const books = [testBook1];
    const wrapper = shallow(
      <BookList books={books} searchString={searchString} />
    );
    expect(wrapper.find("ul").children().length).toEqual(1);
  });

  test("book list is length 2 when 2 books are passed in", () => {
    const books = [testBook1, testBook2];
    const wrapper = shallow(
      <BookList books={books} searchString={searchString} />
    );
    expect(wrapper.find("ul").children().length).toEqual(2);
  });
});
