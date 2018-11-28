Feature: Correctly managing reservations

  Background:
    Given a filled book repository exists
    And an empty reservation table exists
    And a user table with one user exists

  Scenario: A reservation is made on a borrowed book by an authorised user
    When a book is being borrowed by a user
    And a reservation is made on a book by an authorised user
    Then that reservation is added to the database

  Scenario: An availability check is made on a borrowed book with a reservation
    When a book is being borrowed by a user
    And a reservation is made on a book by an authorised user
    Then the status of the book should be reserved with a queue of 1

  Scenario: An availability check is made on a borrowed book with reservations
    When a book is being borrowed by a user
    And a reservation is made on a book by an authorised user
    And a different reservation is made on a book by an authorised user
    Then the status of the book should be reserved with a queue of 2

  Scenario: A book that is available cannot be reserved
    When a book is available
    And a reservation is make on a book by an authorised user
    Then an exception is thrown

  Scenario: When a book is deleted reservations on the book are deleted
    When a reservation is made on a book by an authorised user
    And the book is deleted
    Then the reservation on that book is deleted
