import React from "react";
import { shallow } from "enzyme";
import DashBoard from "../../dashBoard/DashBoard";
import configureStore from "redux-mock-store";

const mockStore = configureStore();

const testUser = { userId: "testUser" };
const inititalState = {
  login: { loggedIn: true, user: { userDetails: testUser } }
};
let store = mockStore(inititalState);

describe("Dashboard renders", () => {
  test("it has correct header", () => {
    const wrapper = shallow(<DashBoard store={store} />);
    const component = wrapper.dive();
    expect(component.find(".pageHeader").exists()).toEqual(true);
    expect(component.find(".pageHeader").text()).toEqual(" Grad Library App ");
  });

  test("A display name is dispayed when logged in", () => {
    const wrapper = shallow(<DashBoard store={store} />);
    const component = wrapper.dive();
    expect(component.find("#displayName").text()).toEqual(
      `Welcome ${testUser.userId}`
    );
  });
});
