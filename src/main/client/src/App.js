import React, { Component } from "react";

import PageRouter from "./pageRouter/PageRouter";
import styles from "./App.module.css";

export default class App extends Component {
  render() {
    return (
      <div className={styles.container}>
        <PageRouter />
      </div>
    );
  }
}
