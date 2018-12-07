import React from "react";
import RequestButton from "../../../../../common/requestButton/RequestButton";
import { shallow } from "enzyme";

const testBook = {
  id: 1,
  popupText: "click to check availability"
};

describe("RequestButton Check Availability Component Tests", () => {
  test("Button calls check book function", () => {
    const checkBook = jest.fn();
    const wrapper = shallow(
      <RequestButton
        buttonState={{
          request: checkBook,
          colour: null,
          buttonText: "Check Availability",
          popupText: "Click to check availability"
        }}
        book={testBook}
      />
    );
    shallow(wrapper.find("Popup").props().trigger).simulate("click");
    expect(checkBook).toHaveBeenCalledWith(testBook);
  });
  test("Button renders with correct styling", () => {
    const checkBook = jest.fn();
    const wrapper = shallow(
      <RequestButton
        buttonState={{
          request: checkBook,
          colour: null,
          buttonText: "Check Availability",
          popupText: "Click to check availability"
        }}
        book={testBook}
      />
    );
    expect(shallow(wrapper.find("Popup").props().trigger).text()).toEqual(
      "Check Availability"
    );
    expect(wrapper.find("Popup").props().content).toEqual(
      "Click to check availability"
    );
  });
});
