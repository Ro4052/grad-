Feature: Correctly deleting books from the database

  Background:
    Given a filled book repository exists

  Scenario: A delete request is received for a book that exists
    When A delete request is received for a book that exists
    Then That book is deleted

  Scenario: A delete request is received for a book that does not exist
    When A delete request is received for a book that does not exist
    Then no books should be deleted

  Scenario: A delete request is received for multiple books that exist
    When A delete request is received for multiple books that exist
    Then those books are deleted

  Scenario: A delete request is received for multiple books, only some of which exist
    When A delete request is received for multiple books, only some of which exist
    Then existing books should be deleted

  Scenario: A delete request is received for multiple books, none of which exist
    When A delete request is received for multiple books, none of which exist
    Then no books should be deleted