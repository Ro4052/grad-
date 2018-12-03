import React from "react";
import { shallow } from "enzyme";

import { Profile } from "../../profile/Profile";

const user = {
  userId: "testUser1",
  name: "Test User 1",
  avatarUrl: "avatar"
};

describe("user details render correctly", () => {
  test("user's avatar is rendered", () => {
    const wrapper = shallow(<Profile user={user} />);
    expect(wrapper.find("img").length).toEqual(1);
  });

  test("username and userId are displayed", () => {
    const wrapper = shallow(<Profile user={user} />);
    expect(wrapper.find("h2").text()).toEqual("Test User 1");
    expect(wrapper.find("h4").text()).toEqual("user ID: testUser1");
  });
});

describe("tabs are rendered", () => {
  test("there is a tab component", () => {
    const wrapper = shallow(<Profile user={user} />);
    expect(wrapper.find("Tab").length).toEqual(1);
  });
});
