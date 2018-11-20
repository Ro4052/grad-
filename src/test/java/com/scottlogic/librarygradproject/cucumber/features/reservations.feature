Feature: Correctly managing reservations

  Background:
    Given a filled book repository exists
    And an empty reservation table exists

  Scenario: A reservation is made on a book by an authorised user
    When a reservation is made on a book by an authorised user
    Then that reservation is added to the database

  Scenario: A reservation is made on a book by an unauthorised user
    When a reservation is made on a book by an unauthorised user
    Then that reservation is not added to the database

  Scenario: An availability check is made on a book with no reservations
    When an availability check is made on a book
    Then the status of the book should be displayed as available

  Scenario: An availability check is made on a book with a reservation
    When a reservation is made on a book by an authorised user
    And an availability check is made on a book
    Then the status of the book should be reserved with a queue of 1

  Scenario: An availability check is made on a book with reservations
    When a reservation is made on a book by an authorised user
    And a reservation is made on a book by an authorised user
    And an availability check is made on a book
    Then the status of the book should be reserved with a queue of 2


