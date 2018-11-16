import React from "react";
import Book from "../../../../dashBoard/bookList/book/Book";
import { shallow } from "enzyme";

const testBook = {
  id: 1,
  title: "Harry Potter",
  author: "JK Rowling",
  isbn: "1000000000",
  publishDate: "1999"
};

describe("Book Component Tests", () => {
  test("title matches test data", () => {
    const wrapper = shallow(<Book book={testBook} />);
    expect(wrapper.find("h3").text()).toBe(testBook.title);
  });

  test("all fields are populated", () => {
    const wrapper = shallow(<Book book={testBook} />);
    expect(wrapper.find("#author1").text()).toBe(`Author: ${testBook.author}`);
    expect(wrapper.find("#isbn1").text()).toBe(`ISBN: ${testBook.isbn}`);
    expect(wrapper.find("#publishDate1").text()).toBe(
      `Publish Date: ${testBook.publishDate}`
    );
  });

  test("reserveBook action called with the right ID", () => {
    const reserveBook = jest.fn();
    const wrapper = shallow(<Book book={testBook} reserveBook={reserveBook} />);
    shallow(wrapper.find(".reservePopup").props().trigger).simulate("click");
    expect(reserveBook).toHaveBeenCalledWith(testBook.id);
  });

  test("reserve popup has the right text", () => {
    const wrapper = shallow(
      <Book book={testBook} reservePopText="Test text" />
    );
    expect(wrapper.find(".reservePopup").props().content).toBe("Test text");
  });

  test("Available check action called with the right ID", () => {
    const checkBook = jest.fn();
    const wrapper = shallow(<Book book={testBook} checkBook={checkBook} />);
    shallow(wrapper.find(".availablePopup").props().trigger).simulate("click");
    expect(checkBook).toHaveBeenCalledWith(testBook.id);
  });

  test("available popup has the right text", () => {
    const wrapper = shallow(
      <Book book={testBook} checkBookPopText="Test Text" />
    );
    expect(wrapper.find(".availablePopup").props().content).toBe("Test Text");
  });
});
