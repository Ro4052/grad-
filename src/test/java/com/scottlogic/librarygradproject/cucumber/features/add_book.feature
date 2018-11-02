Feature: Correctly adding books to the database

  Background:
    Given a book repository exists

  Scenario: An add book request is received with all fields correctly filled
    When An add book request is received with correct book details
    Then the book should enter the database

  Scenario: An add book request is received with incorrect ISBN value
    When An add book request is received with incorrect ISBN
    Then the book should not enter the database

  Scenario: An add book request is received without author value
    When An add book request is received without author
    Then the book should not enter the database

  Scenario: An add book request is received without title value
    When An add book request is received without title
    Then the book should not enter the database

  Scenario: An add book request is received with author too long
    When An add book request is received with author too long
    Then the book should not enter the database

  Scenario: An add book request is received with title too long
    When An add book request is received with title too long
    Then the book should not enter the database

  Scenario: An add book request is received with incorrect date format
    When An add book request is received with incorrect date format
    Then the book should not enter the database

  Scenario: An add book request is received with leading and trailing whitespace in fields but otherwise acceptable
    When An add book request is received with leading and trailing whitespace in fields otherwise acceptable
    And the book should enter the database

  Scenario: An add book request is received with leading and trailing whitespace in fields and otherwise unacceptable
    When An add book request is received with leading and trailing whitespace in fields and unacceptable
    And the book should not enter the database