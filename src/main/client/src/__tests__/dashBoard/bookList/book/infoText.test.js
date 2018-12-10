import React from "react";
import { shallow } from "enzyme";
import InfoText from "../../../../dashBoard/bookList/book/InfoText";

const book = {};
const reservation = { collectBy: "2018-12-24" };
const borrow = { returnDate: "2018-12-15" };

describe("Info Component tests", () => {
  test("Renders correct information with collection pending", () => {
    const book1 = book;
    book1.collectState = "Collector - Collection Not Started";
    const wrapper = shallow(
      <InfoText book={book1} reservation={reservation} />
    );
    expect(wrapper.find("Popup").props().content).toBe(
      "You must collect this book by Mon 24th Dec 2018, else your reservation will be cancelled"
    );
  });

  test("Renders correct information with return pending", () => {
    const book2 = book;
    book2.state = "Borrower - Return Not Started";
    const wrapper = shallow(<InfoText book={book2} borrow={borrow} />);
    expect(wrapper.find("Popup").props().content).toBe(
      "You must return this book by Sat 15th Dec 2018"
    );
  });

  test("Renders correct information with book available", () => {
    const book3 = book;
    book3.state = "Available to Borrow";
    const wrapper = shallow(<InfoText book={book3} />);
    expect(wrapper.find("Popup").props().content).toBe(
      "You can borrow this book until Mon 17th Dec 2018"
    );
  });
});
