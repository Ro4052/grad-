import React from "react";
import { shallow } from "enzyme";
import SearchBar from "../../../dashBoard/searchBar/SearchBar";

describe("Search Bar renders as default", () => {
  test("there is a dropdown menu", () => {
    const wrapper = shallow(<SearchBar />);
    expect(wrapper.find("select").exists()).toBe(true);
  });

  test("the dropdown menu has 5 options", () => {
    const wrapper = shallow(<SearchBar />);
    expect(wrapper.find("select").children().length).toEqual(5);
  });

  test("there is a search icon rendered", () => {
    const wrapper = shallow(<SearchBar />);
    expect(wrapper.find("Icon").exists()).toBe(true);
  });

  test("there is a search input field", () => {
    const wrapper = shallow(<SearchBar />);
    expect(wrapper.find(".searchBar").exists()).toBe(true);
  });

  test("the search input field renders with an empty string", () => {
    const wrapper = shallow(<SearchBar />);
    expect(wrapper.find(".searchBar").text()).toEqual("");
  });

  test("the search input has a placeholder of 'Search'", () => {
    const wrapper = shallow(<SearchBar />);
    expect(wrapper.find(".searchBar").props().placeholder).toEqual("Search");
  });
});

describe("Search Bar renders with range selection for publish date search option", () => {
  test("there is date search container when searching by publish date", () => {
    const searchBy = "publishDate";
    const wrapper = shallow(<SearchBar searchBy={searchBy} />);
    expect(wrapper.find(".dateSearchContainer").exists()).toBe(true);
  });

  test("there is two date input fields when searching by publish date", () => {
    const searchBy = "publishDate";
    const wrapper = shallow(<SearchBar searchBy={searchBy} />);
    expect(wrapper.find(".dateEntry").length).toEqual(2);
    expect(wrapper.find(".dateLabel").length).toEqual(2);
  });

  test("the upper date field is auto populated with the current year", () => {
    const searchBy = "publishDate";
    const upperDate = new Date().getFullYear();
    const wrapper = shallow(
      <SearchBar searchBy={searchBy} upperDate={upperDate} />
    );
    expect(
      wrapper
        .find(".dateSearchContainer")
        .childAt(3)
        .props().value
    ).toEqual(new Date().getFullYear());
  });

  test("the upper date field has a placeholder of the current year", () => {
    const searchBy = "publishDate";
    const upperDate = new Date().getFullYear();
    const wrapper = shallow(
      <SearchBar searchBy={searchBy} upperDate={upperDate} />
    );
    expect(
      wrapper
        .find(".dateSearchContainer")
        .childAt(3)
        .props().placeholder
    ).toEqual(new Date().getFullYear());
  });

  test("the lower date field is auto populated with an empty string", () => {
    const searchBy = "publishDate";
    const lowerDate = "";
    const wrapper = shallow(
      <SearchBar searchBy={searchBy} lowerDate={lowerDate} />
    );
    expect(
      wrapper
        .find(".dateSearchContainer")
        .childAt(1)
        .props().value
    ).toEqual("");
  });

  test("the lower date field has a placeholder of '----'", () => {
    const searchBy = "publishDate";
    const lowerDate = "";
    const wrapper = shallow(
      <SearchBar searchBy={searchBy} lowerDate={lowerDate} />
    );
    expect(
      wrapper
        .find(".dateSearchContainer")
        .childAt(1)
        .props().placeholder
    ).toEqual("----");
  });
});
