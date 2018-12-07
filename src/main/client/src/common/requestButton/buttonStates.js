const buttonStates = (props, book) => {
  let request,
    colour,
    buttonText,
    popupText,
    basic = false,
    disabled = false;

  switch (book.state) {
    case "Borrower - Return Not Started":
      request = props.startProcess;
      colour = "red";
      buttonText = "Return";
      popupText = "Return your book";
      break;

    case "Borrower - Return Started":
      request = props.returnBook;
      colour = "red";
      buttonText = "Confirm";
      basic = true;
      popupText = "Are you sure?";
      break;

    case "Reserver - Cancel Not Started":
      request = props.startProcess;
      colour = "red";
      buttonText = "Cancel";
      popupText = "Cancel your reservation";
      break;

    case "Reserver - Cancel Started":
      request = props.cancelReservation;
      colour = "red";
      buttonText = "Confirm";
      basic = true;
      popupText = "Are you sure?";
      break;

    case "Book deleted":
      request = () => {};
      buttonText = "Deleted";
      disabled = true;
      break;

    case "Available to Borrow":
      request = props.borrowBook;
      colour = "green";
      buttonText = "Borrow";
      popupText = "Available";
      break;

    case "Available to Reserve":
      request = props.reserveBook;
      colour = "blue";
      buttonText = "Reserve";
      popupText = `Number of reservations: ${book.queueLength}`;
      break;

    case "Something went wrong":
      request = props.checkBook;
      colour = null;
      buttonText = "Check Availability";
      popupText = "Something went wrong, please try again";
      break;

    default:
      request = props.checkBook;
      colour = null;
      buttonText = "Check Availability";
      popupText = "Click to check availability";
      break;
  }
  return { request, colour, buttonText, disabled, basic, popupText };
};

export default buttonStates;
