import React from "react";

import scottLogicLogo from "../SL_primary_AW_POS_LO_RGB.jpg";
import Login from "../../login/Login";
import styles from "./PageHeader.module.css";

const PageHeader = props => (
  <div className={styles.navBar}>
    <img src={scottLogicLogo} alt="Scott Logic Logo" className={styles.logo} />
    <h1 className={styles.pageHeader}> Grad Library App </h1>
    <div className={styles.profileActions}>
      {props.loggedIn && (
        <div id="displayName">
          Welcome, {props.user.name || props.user.userId}
        </div>
      )}
      <Login />
    </div>
  </div>
);

export default PageHeader;
