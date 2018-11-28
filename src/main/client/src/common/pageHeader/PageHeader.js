import React from "react";
import { Image } from "semantic-ui-react";

import history from "../../history";
import scottLogicLogo from "../SL_primary_AW_POS_LO_RGB.jpg";
import Login from "../../login/Login";
import styles from "./PageHeader.module.css";

const PageHeader = props => (
  <div className={styles.navBar}>
    <img
      size="small"
      src={scottLogicLogo}
      alt="Scott Logic Logo"
      className={styles.logo}
      onClick={() => history.push("/dashboard")}
    />
    <h1 className={styles.pageHeader}> Grad Library App </h1>
    <div className={styles.profileActions}>
      {props.loggedIn && (
        <h4 id="displayName" onClick={() => history.push("/profile")}>
          Welcome, {props.user.name || props.user.userId}
        </h4>
      )}
      <Login />
    </div>
  </div>
);

export default PageHeader;
