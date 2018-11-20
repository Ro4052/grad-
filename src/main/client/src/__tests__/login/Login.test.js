import React from "react";
import { shallow } from "enzyme";
import Login from "../../login/Login";
import configureStore from "redux-mock-store";

const mockStore = configureStore();

const initialState = { login: {} };
let store;

describe("Login component renders", () => {
  test("renders with the correct button when logged in", () => {
    const state = initialState;
    state.login.loggedIn = true;
    store = mockStore(state);
    const wrapper = shallow(<Login store={store} />);
    const component = wrapper.dive();
    expect(component.find("Button").exists()).toEqual(true);
    expect(component.find("Button").props().content).toEqual("Logout");
  });

  test("renders with the correct button when logged out", () => {
    const state = initialState;
    state.login.loggedIn = false;
    store = mockStore(state);
    const wrapper = shallow(<Login store={store} />);
    const component = wrapper.dive();
    expect(component.find("Button").exists()).toEqual(true);
    expect(component.find("Button").props().content).toEqual("Login");
  });
});
