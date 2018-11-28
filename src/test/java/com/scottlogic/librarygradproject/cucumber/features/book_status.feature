Scenario: An availability check is made on a book with no reservations
    Then the status of the book should be available

Scenario: An availibility check is made on a book with no borrows
    Then the status of the book should be available