import React from 'react';
import { shallow } from "enzyme";
import DeleteBookModal from "../../../common/modals/DeleteBookModal";

describe('Modal renders when open', () => {
    test('modal renders correct heading', () => {
        const wrapper = shallow(<DeleteBookModal/>);
        expect(wrapper.find("#modalHeader").exists()).toEqual(true);
        expect(wrapper.find("#modalHeader").render().text()).toEqual("Delete Selected Books");
    })

    test("modal renders yes and no buttons", () => {
        const wrapper = shallow(<DeleteBookModal/>);
        expect(wrapper.find("#noBtn").render().text()).toEqual("Cancel");
        expect(wrapper.find("#yesBtn").render().text()).toEqual("Delete");
    })

    test("Delete button calls deleteBooks function", () => {
        const deleteBook = jest.fn();
        const toggleSelectMode = jest.fn();
        const wrapper = shallow(<DeleteBookModal deleteBook={deleteBook} toggleSelectMode={toggleSelectMode}/>);
        wrapper.find("#yesBtn").prop("onClick")();
        expect(deleteBook).toHaveBeenCalled();
        expect(toggleSelectMode).toHaveBeenCalled();
    })

    test("Cancel button sets modal open state to false", () => {
        const wrapper = shallow(<DeleteBookModal />);
        wrapper.setState({open: true});
        wrapper.find("#noBtn").prop("onClick")();
        expect(wrapper.state("open")).toEqual(false);
    })
})