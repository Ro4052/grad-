import React from "react";
import { shallow } from "enzyme";
import DeleteBookModal from "../../../common/modals/DeleteBookModal";

describe("Modal renders when open", () => {
  test("modal renders correct heading", () => {
    const deleteList = [];
    const wrapper = shallow(<DeleteBookModal deleteList={deleteList} />);
    expect(wrapper.find("#modalHeader").exists()).toEqual(true);
    expect(
      wrapper
        .find("#modalHeader")
        .render()
        .text()
    ).toEqual("Delete Selected Books");
  });

  test("modal renders yes and no buttons", () => {
    const deleteList = [];
    const wrapper = shallow(<DeleteBookModal deleteList={deleteList} />);
    expect(
      wrapper
        .find("#noBtn")
        .render()
        .text()
    ).toEqual("Cancel");
    expect(
      wrapper
        .find("#yesBtn")
        .render()
        .text()
    ).toEqual("Delete");
  });

  test("Delete button calls deleteBooks function", () => {
    const deleteBook = jest.fn();
    const deleteList = [];
    const toggleSelectMode = jest.fn();
    const wrapper = shallow(
      <DeleteBookModal
        deleteList={deleteList}
        deleteBook={deleteBook}
        toggleSelectMode={toggleSelectMode}
      />
    );
    wrapper.find("#yesBtn").prop("onClick")();
    expect(deleteBook).toHaveBeenCalled();
    expect(toggleSelectMode).toHaveBeenCalled();
  });

  test("Cancel button sets modal open state to false", () => {
    const deleteList = [];
    const wrapper = shallow(<DeleteBookModal deleteList={deleteList} />);
    wrapper.setState({ open: true });
    wrapper.find("#noBtn").prop("onClick")();
    expect(wrapper.state("open")).toEqual(false);
  });
});
