Feature: Correctly managing borrowing

  Background:
    Given a filled book repository exists
    And a user table with one user exists
    And an empty reservation table exists
    And an empty borrow table exists

  Scenario: A book can be borrowed by an authorised user
    When a borrow request is made on an available book by an authorised user
    Then that borrow is added to the database

  Scenario: A book cannot be borrowed concurrently by different users
    When a borrow request is made on an available book by an authorised user
    And then another borrow request is made for the same book by an authorised user
    Then an exception is thrown

  Scenario: When a book is borrowed the return date is set to 7 days from the borrow date
    When a borrow request is made on an available book by an authorised user
    Then the return date is set to seven days from the current day

  Scenario: A book is auto returned after seven days of the borrow date
    When a borrow request is made on an available book by an authorised user
    Then the book is auto returned at midnight after a week

  Scenario: The top reservation in the queue is moved to borrow when the book is auto returned
    When a borrow request is made on an available book by an authorised user
    And a reservation is made on a book by an authorised user
    Then when the book is auto returned the reservation moves to borrowed

  Scenario: When a book is deleted borrows on the book are deleted
    When a reservation is made on a book by an authorised user
    And the book is deleted
    Then the reservation on that book is deleted