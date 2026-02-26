Feature: Booking Management API
  As a user, I want to manage hotel bookings through the API.

  Scenario: Health check of the booking service
    Given the booking API is up and running

  Scenario: Successfully create a new booking
    When I create a booking with valid details:
      | firstname | lastname | roomid | depositpaid | checkin    | checkout   | email           | phone       |
      | Jean      | Dupont   | 101    | true        | 2025-10-13 | 2025-10-15 | jean@test.com   | 01234567890 |
    Then the status code should be 200
    And the response should contain a booking ID

  Scenario: Validation error when email is invalid
    When I create a booking with an invalid email "not-an-email"
    Then the status code should be 400
    And the response should contain an error message "must be a well-formed email address"