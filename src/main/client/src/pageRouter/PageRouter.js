import React, { Component } from "react";
import { Switch, Route, Redirect } from "react-router-dom";

import Dashboard from "../dashBoard/DashBoard";
import Profile from "../profile/Profile";

export default class PageRouter extends Component {
  render() {
    return (
      <Switch>
        <Route path="/dashboard" component={Dashboard} />
        <Route path="/profile" component={Profile} />
        <Redirect to="/dashboard" />
      </Switch>
    );
  }
}
