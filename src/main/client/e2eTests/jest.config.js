module.exports = {
  preset: "jest-puppeteer",
  globalSetup: "./setup.js",
  globalTeardown: "./teardown.js",
  testEnvironment: "./puppeteer_environment.js",
  jest: {
    scriptPreprocessor: "../node_modules/jest-css-modules",
    transform: {
      ".+\\.(css|styl|less|sass|scss)$":
        "../node_modules/jest-css-modules-transform"
    }
  },
  transform: {
    ".+\\.(css|styl|less|sass|scss)$":
      "../node_modules/jest-css-modules-transform"
  }
};
