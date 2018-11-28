import React from "react";
import RequestButton from "../../../../../dashBoard/bookList/book/requestButton/RequestButton";
import { shallow } from "enzyme";

const testBook = {
  id: 1,
  popupText: "click to check availability"
};

describe("RequestButton Check Availability Component Tests", () => {
  test("Button renders with correct styling", () => {
    const checkBook = jest.fn();
    const wrapper = shallow(
      <RequestButton
        buttonText="Check Availability"
        request={checkBook}
        colour={null}
        book={testBook}
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
        buttonText="Check Availability"
        request={checkBook}
        colour={null}
        book={testBook}
      />
    );
    shallow(wrapper.find("Popup").props().trigger).simulate("click");
    expect(checkBook).toHaveBeenCalledWith(testBook);
  });
});

describe("RequestButton Reserve Component Tests", () => {
  test("Button renders with correct styling", () => {
    const reserveBook = jest.fn();
    testBook.popupText = "number of reservations: 0";
    const wrapper = shallow(
      <RequestButton
        buttonText="Reserve"
        request={reserveBook}
        colour="blue"
        book={testBook}
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
    testBook.popupText = "number of reservations: 0";
    const wrapper = shallow(
      <RequestButton
        buttonText="Reserve"
        request={reserveBook}
        colour="blue"
        book={testBook}
      />
    );
    shallow(wrapper.find("Popup").props().trigger).simulate("click");
    expect(reserveBook).toHaveBeenCalledWith(testBook);
  });
});

describe("RequestButton Borrow Component Tests", () => {
  test("Button renders with correct styling", () => {
    const borrowBook = jest.fn();
    testBook.popupText = "Available";
    const wrapper = shallow(
      <RequestButton
        buttonText="Borrow"
        request={borrowBook}
        colour="green"
        book={testBook}
      />
    );
    expect(shallow(wrapper.find("Popup").props().trigger).text()).toEqual(
      "Borrow"
    );
    expect(wrapper.find("Popup").props().content).toEqual("Available");
  });

  test("Button calls borrow book function", () => {
    const borrowBook = jest.fn();
    testBook.popupText = "number of reservations: 0";
    const wrapper = shallow(
      <RequestButton
        buttonText="Reserve"
        request={borrowBook}
        colour="blue"
        book={testBook}
      />
    );
    shallow(wrapper.find("Popup").props().trigger).simulate("click");
    expect(borrowBook).toHaveBeenCalledWith(testBook);
  });
});
