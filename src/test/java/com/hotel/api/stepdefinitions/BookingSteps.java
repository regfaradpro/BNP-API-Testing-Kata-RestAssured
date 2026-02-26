package com.hotel.api.stepdefinitions;

import com.hotel.api.clients.BookingClient;
import com.hotel.api.models.Booking;
import com.hotel.api.models.BookingDates;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.Map;

public class BookingSteps {
    private BookingClient bookingClient = new BookingClient();
    private Response lastResponse;

    @Given("the booking API is up and running")
    public void checkHealth() {
        bookingClient.getHealth().then().statusCode(200).body("status", equalTo("UP"));
    }

    @When("I create a booking with valid details:")
    public void createValidBooking(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> data = dataTable.asMaps().get(0);
        Booking booking = new Booking();
        booking.setFirstname(data.get("firstname"));
        booking.setLastname(data.get("lastname"));
        booking.setRoomid(Integer.parseInt(data.get("roomid")));
        booking.setDepositpaid(Boolean.parseBoolean(data.get("depositpaid")));
        booking.setBookingdates(new BookingDates(data.get("checkin"), data.get("checkout")));
        booking.setEmail(data.get("email"));
        booking.setPhone(data.get("phone"));

        lastResponse = bookingClient.createBooking(booking);
    }

    @When("I create a booking with an invalid email {string}")
    public void createBookingInvalidEmail(String email) {
        Booking booking = new Booking();
        booking.setFirstname("Test");
        booking.setLastname("User");
        booking.setRoomid(1);
        booking.setDepositpaid(true);
        booking.setBookingdates(new BookingDates("2025-01-01", "2025-01-02"));
        booking.setEmail(email);
        booking.setPhone("12345678910");

        lastResponse = bookingClient.createBooking(booking);
    }

    @Then("the status code should be {int}")
    public void verifyStatusCode(int code) {
        lastResponse.then().statusCode(code);
    }

    @Then("the response should contain a booking ID")
    public void verifyBookingId() {
        lastResponse.then().body("bookingid", notNullValue());
    }

    @Then("the response should contain an error message {string}")
    public void verifyErrorMessage(String message) {
        lastResponse.then().body("errors", hasItem(message));
    }
}