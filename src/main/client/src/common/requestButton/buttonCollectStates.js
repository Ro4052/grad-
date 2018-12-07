const buttonCollectStates = (props, book) => {
  let request,
    colour,
    buttonText,
    popupText,
    basic = false;

  switch (book.collectState) {
    case "Collector - Collection Started":
      request = props.collectBook;
      colour = "green";
      buttonText = "Confirm";
      popupText = "Are you sure?";
      basic = true;
      break;

    case "Collection went wrong":
      request = props.startCollection;
      colour = "green";
      buttonText = "Collect";
      popupText = "Something went wrong, please try again";
      break;

    default:
      //Collector - Collection Not Started
      request = props.startCollection;
      colour = "green";
      buttonText = "Collect";
      popupText = `Click to collect ${book.title}`;
      break;
  }
  return { request, colour, buttonText, basic, popupText };
};

export default buttonCollectStates;
