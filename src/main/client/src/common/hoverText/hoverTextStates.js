const moment = require("moment");

const hoverTextStates = (bookState, returnBy) => {
  let text;

  switch (bookState) {
    case "Borrower - Return Not Started":
      text = `You have until ${returnBy} to return this book`;
      break;

    case "Available to Borrow":
      text = `You can borrow this book until ${moment()
        .add(7, "days")
        .format("ddd Do MMM YYYY")}`;
      break;

    // case "Collector - Collection Not Started":
    //   text = `You must collect this book by ${moment(
    //     collectBy
    //   ).format("ddd Do MMM YYYY")}, else your reservation will be cancelled`;

    default:
      text = "information not found...";
      break;
  }
  return text;
};

export default hoverTextStates;
