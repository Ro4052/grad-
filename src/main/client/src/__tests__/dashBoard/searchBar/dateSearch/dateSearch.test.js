import React from "react";
import { shallow } from "enzyme";
import DateSearch from "../../../../dashBoard/searchBar/dateSearch/DateSearch";

describe("The lower date input field renders correctly", () => {
  test("the lower date field is auto populated with an empty string", () => {
    const value = "";
    const id = "lowerDate";
    const wrapper = shallow(<DateSearch value={value} id={id} />);
    expect(wrapper.find("input").props().value).toEqual("");
  });

  test("the lower date field has a placeholder of '----'", () => {
    const value = "";
    const id = "lowerDate";
    const placeholder = "----";
    const wrapper = shallow(
      <DateSearch value={value} id={id} placeholder={placeholder} />
    );
    expect(wrapper.find("input").props().placeholder).toEqual("----");
  });
});

describe("The upper date input field renders correctly", () => {
  test("the upper date field is auto populated with the current year", () => {
    const value = new Date().getFullYear();
    const id = "upperDate";
    const wrapper = shallow(<DateSearch value={value} id={id} />);
    expect(wrapper.find("input").props().value).toEqual(
      new Date().getFullYear()
    );
  });

  test("the upper date field has a placeholder of the current year", () => {
    const value = new Date().getFullYear();
    const id = "upperDate";
    const wrapper = shallow(<DateSearch value={value} id={id} />);
    expect(wrapper.find("input").props().placeholder).toEqual(
      new Date().getFullYear()
    );
  });
});
