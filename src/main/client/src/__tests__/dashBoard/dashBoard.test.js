import React from 'react';
import { shallow } from "enzyme";
import store from "../../store/store";
import DashBoard from "../../dashBoard/DashBoard";

describe('Dashboard renders', () => {
    test('it has correct header', () => {
        const wrapper = shallow(<DashBoard store={store} />);
        expect(wrapper.find(".pageHeader").exists()).toEqual(true);
        expect(wrapper.find(".pageHeader").text()).toEqual(" Grad Library App ")
    })
})