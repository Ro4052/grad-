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
        expect(wrapper.find("#noBtn").render().text()).toEqual("No");
        expect(wrapper.find("#yesBtn").render().text()).toEqual("Yes");
    })

    test("Yes button calls deleteBooks function", () => {
        const deleteBook = jest.fn();
        const clearDeleteList = jest.fn();
        const wrapper = shallow(<DeleteBookModal deleteBook={deleteBook} clearDeleteList={clearDeleteList}/>);
        wrapper.find("#yesBtn").prop("onClick")();
        expect(deleteBook).toHaveBeenCalled();
        expect(clearDeleteList).toHaveBeenCalled();
    })
})