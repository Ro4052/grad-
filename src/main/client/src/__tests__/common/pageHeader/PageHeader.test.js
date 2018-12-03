import React from "react";
import { shallow } from "enzyme";
import PageHeader from "../../../common/pageHeader/PageHeader";

const testUser = { userId: "testUser" };

describe("Page Header renders", () => {
  test("it has correct header", () => {
    const wrapper = shallow(<PageHeader user={testUser} loggedIn={true} />);
    expect(wrapper.find(".pageHeader").exists()).toEqual(true);
    expect(wrapper.find(".pageHeader").text()).toEqual(" Grad Library App ");
  });

  test("A display name is dispayed when logged in", () => {
    const wrapper = shallow(<PageHeader user={testUser} loggedIn={true} />);
    expect(wrapper.find("#displayName").text()).toEqual(
      `Welcome, ${testUser.userId}<Image />`
    );
  });

  test("No welcome message displays when logged out", () => {
    const wrapper = shallow(<PageHeader user={testUser} loggedIn={false} />);
    expect(wrapper.find("#displayName").exists()).toBe(false);
  });
});
