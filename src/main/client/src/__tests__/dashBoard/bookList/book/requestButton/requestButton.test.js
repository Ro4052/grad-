import React from "react";
import RequestButton from "../../../../../dashBoard/bookList/book/requestButton/RequestButton";
import { shallow } from "enzyme";

const testBook = {
  id: 1
};

describe("RequestButton Check Availability Component Tests", () => {
  test("Button renders with correct styling", () => {
    const checkBook = jest.fn();
    const wrapper = shallow(
      <RequestButton
        bookId={testBook.id}
        buttonText="Check Availability"
        request={checkBook}
        colour={null}
        popupText="click to check availability"
      />
    );
    expect(shallow(wrapper.find("Popup").props().trigger).text()).toEqual(
      "Check Availability"
    );
    expect(wrapper.find("Popup").props().content).toEqual(
      "click to check availability"
    );
  });

  test("Button calls check book function", () => {
    const checkBook = jest.fn();
    const wrapper = shallow(
      <RequestButton
        bookId={testBook.id}
        buttonText="Check Availability"
        request={checkBook}
        colour={null}
        popupText="click to check availability"
      />
    );
    shallow(wrapper.find("Popup").props().trigger).simulate("click");
    expect(checkBook).toHaveBeenCalledWith(testBook.id);
  });
});

describe("RequestButton Reserve Component Tests", () => {
  test("Button renders with correct styling", () => {
    const reserveBook = jest.fn();
    const wrapper = shallow(
      <RequestButton
        bookId={testBook.id}
        buttonText="Reserve"
        request={reserveBook}
        colour="blue"
        popupText="number of reservations: 0"
      />
    );
    expect(shallow(wrapper.find("Popup").props().trigger).text()).toEqual(
      "Reserve"
    );
    expect(wrapper.find("Popup").props().content).toEqual(
      "number of reservations: 0"
    );
  });

  test("Button calls reserve book function", () => {
    const reserveBook = jest.fn();
    const wrapper = shallow(
      <RequestButton
        bookId={testBook.id}
        buttonText="Reserve"
        request={reserveBook}
        colour="blue"
        popupText="number of reservations: 0"
      />
    );
    shallow(wrapper.find("Popup").props().trigger).simulate("click");
    expect(reserveBook).toHaveBeenCalledWith(testBook.id);
  });
});

describe("RequestButton Borrow Component Tests", () => {
  test("Button renders with correct styling", () => {
    const borrowBook = jest.fn();
    const wrapper = shallow(
      <RequestButton
        bookId={testBook.id}
        buttonText="Borrow"
        request={borrowBook}
        colour="green"
        popupText="Available"
      />
    );
    expect(shallow(wrapper.find("Popup").props().trigger).text()).toEqual(
      "Borrow"
    );
    expect(wrapper.find("Popup").props().content).toEqual("Available");
  });

  test("Button calls borrow book function", () => {
    const borrowBook = jest.fn();
    const wrapper = shallow(
      <RequestButton
        bookId={testBook.id}
        buttonText="Reserve"
        request={borrowBook}
        colour="blue"
        popupText="number of reservations: 0"
      />
    );
    shallow(wrapper.find("Popup").props().trigger).simulate("click");
    expect(borrowBook).toHaveBeenCalledWith(testBook.id);
  });
});
