import React from "react";
import { shallow } from "enzyme";
// import store from "../../store/store";
import DashBoard from "../../dashBoard/DashBoard";
import configureStore from "redux-mock-store";

const mockStore = configureStore();

const testUser = { username: "testUser" };
const inititalState = { login: { loggedIn: true, user: testUser } };
let store = mockStore(inititalState);

describe("Dashboard renders", () => {
  test("it has correct header", () => {
    const wrapper = shallow(<DashBoard store={store} />);
    const component = wrapper.dive();
    expect(component.find(".pageHeader").exists()).toEqual(true);
    expect(component.find(".pageHeader").text()).toEqual(" Grad Library App ");
  });

  test("Username is dispayed when logged in", () => {
    const wrapper = shallow(<DashBoard store={store} />);
    const component = wrapper.dive();
    expect(component.find("#username").text()).toEqual(
      `Welcome ${testUser.username}`
    );
  });
});
