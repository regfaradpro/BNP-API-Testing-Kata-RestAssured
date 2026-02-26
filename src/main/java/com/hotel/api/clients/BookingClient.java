package com.hotel.api.clients;

import com.hotel.api.models.Booking;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class BookingClient {
    private static final String BASE_URL = "https://automationintesting.online/api";

    public Response getHealth() {
        return RestAssured.get(BASE_URL + "/booking/actuator/health");
    }

    public Response createBooking(Booking booking) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(booking)
                .post(BASE_URL + "/booking");
    }

    public Response getBookingById(int id) {
        return RestAssured.get(BASE_URL + "/booking/" + id);
    }
}