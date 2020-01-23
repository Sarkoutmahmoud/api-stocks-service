Feature: 02 01 Happy flow of retrieving all stocks.

  As an user I want to receive all stocks hold by the stocks service when I call the stocks endpoint.

  Scenario: Retrieve all stocks.
    When I request all the stocks from the application
    Then status code "200" is returned
    And the response contains a list of three stocks

  Scenario: Retrieve a specific stock
    When I request a stock with id "1"
    Then status code "200" is returned
    And a proper stock Object is returned.

  Scenario: Retrieve a specific stock history
    When I request a stock  history with id "1"
    Then status code "200" is returned
    And a proper stock history Object is returned.