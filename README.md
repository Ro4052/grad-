<!-- prettier-ignore-start -->
# Bristol Grad Library Project 2018

A group project to develop an app for the office library for 2018 Bristol grads.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

Essential

- Node.js
- Java JDK 8

Suggested

- VSCode for front-end development
- IntelliJ IDEA for back-end development

### Maven Setup

For Java, setup is fairly simple with IntelliJ

- Open the project at the route directory
- In settings, tell IntelliJ where to find the SDK
- It is also recommended that you install the lombok plugin
- The Java program can then be built and deployed from IntelliJ.

### Node Setup

For front-end dependencies run the following command in _/src/main/client_:

```
npm i
```

To run a development version of the app you can use:

```
npm start
```

To setup automatic linting in VSCode you can refer to [this tutorial](https://www.youtube.com/watch?v=bfyI9yl3qfE) or follow these steps:

- Install the _ESLint_ plugin from Dirk Baeumer and reload
- Install the _Prettier - Code formatter_ plugin from Esben Petersen and reload
- Go to settings

```
Ctrl + ,
```

- Click the three dots on the top left and select _Open settings.json_
- Insert the following options into the settings json:

```
"editor.formatOnSave": true,
"[javascript]": {
  "editor.formatOnSave": false
},
"eslint.autoFixOnSave": true,
"eslint.alwaysShowStatus": true,
"prettier.disableLanguages": [
  "js"
]
```

## Local Tests

### Maven Tests

Maven unit tests can be run directly from IntelliJ.

### Node Tests

Unit tests can be run with this commmand:

```
npm test
```

## Version Control

- This repository has two protected branches, _Master_ and _Develop_
- All tests must pass before a branch can merge into either of these
- Develop requires a code review with at least two approvals
- New front-end code will be automatically linted using [Husky](https://github.com/typicode/husky) and [Prettier](https://prettier.io/)
- Feature branches should be named as such: _[story number]-[appropriate name]_
- Feature branches should be deleted after the pull request has been merged

## Deployment

Both _Master_ and _Develop_ have automatic deployments on Heroku

- [Master](https://bristol-library.herokuapp.com/)
- [Develop](https://bristol-library-dev.herokuapp.com/)

<!-- prettier-ignore-end -->
