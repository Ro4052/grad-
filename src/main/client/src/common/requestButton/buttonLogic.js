const buttonLogic = props => {
  const book = props.book;
  let request, colour, buttonText;
  switch (book.role) {
    case "Borrower":
      if (!book.processStarted) {
        request = props.startProcess;
        colour = "red";
        buttonText = "Return";
      } else {
        request = props.returnBook;
        colour = "red";
        buttonText = "Confirm";
      }
      break;
    case "Reserver":
      if (!book.processStarted) {
        request = props.startProcess;
        colour = "red";
        buttonText = "Cancel";
      } else {
        request = props.cancelReservation;
        colour = "red";
        buttonText = "Confirm";
      }
      break;
    default:
      if (book.availabilityChecked) {
        request = book.isAvailable ? props.borrowBook : props.reserveBook;
        colour = book.isAvailable ? "green" : "blue";
        buttonText = book.isAvailable ? "Borrow" : "Reserve";
      } else {
        request = props.checkBook;
        colour = null;
        buttonText = "Check Availability";
      }
      break;
  }
  return { request, colour, buttonText };
};

export default buttonLogic;
