import React from "react";
import { shallow } from "enzyme";
import store from "../../store/store";
import DashBoard from "../../dashBoard/DashBoard";

describe("Dashboard renders", () => {
  test("it has correct header", () => {
    const wrapper = shallow(<DashBoard store={store} />);
    const component = wrapper.dive();
    expect(component.find(".pageHeader").exists()).toEqual(true);
    expect(component.find(".pageHeader").text()).toEqual(" Grad Library App ");
  });
});
