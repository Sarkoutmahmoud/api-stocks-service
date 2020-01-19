Feature: 01 01 Validate Unauthenticated health check access

    As a developer I want to have an health endpoint that does not expose details regarding the application.

    Scenario: Perform a health check without authentication
        When I request the health endpoint of the API
        Then status code "200" is returned
        And the health check returns UP
        And the health response contains no details