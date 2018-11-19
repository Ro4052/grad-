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

  test("there is two date input fields with labels when searching by publish date", () => {
    const searchBy = "publishDate";
    const wrapper = shallow(<SearchBar searchBy={searchBy} />);
    expect(wrapper.find("DateSearch").length).toEqual(2);
    expect(wrapper.find(".dateLabel").length).toEqual(2);
  });
});
