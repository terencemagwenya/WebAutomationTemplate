package mfstestcases;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.*;
public class Q2ApiTesting {
    static Response response;
    static final String contentType = "application/json; charset=UTF-8";
    static final String projectFilePath = System.getProperty("user.dir");
    static final String jsonPayloadFilePath = projectFilePath + "\\jsonfiles\\";
    @Test(priority = 1)
    public void CreateBooking() {
        baseURI = "https://restful-booker.herokuapp.com";
        CreateResourceData jsonPayload = new CreateResourceData();
        jsonPayload.firstname = "Terence";
        jsonPayload.lastname = "Magwenya";
        jsonPayload.totalprice = 2500;
        jsonPayload.depositpaid = true;
        BookingDatesData bookingDates = new BookingDatesData();
        jsonPayload.bookingdates = bookingDates;
        bookingDates.checkin = "2020-01-01";
        bookingDates.checkout = "2021-01-01";
        jsonPayload.additionalneeds = "Breakfast";
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonPayload);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        response = given().
                header("Content-Type", contentType).
                body(jsonString).log().all().
                when().
                post("/booking/");
        System.out.println("The response code is : " + response.getStatusCode());
        System.out.println("The response body is as below : ");
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().get("booking.firstname"), jsonPayload.getFirstname());
        Assert.assertEquals(response.jsonPath().get("booking.lastname"), jsonPayload.getLastname());
        Assert.assertEquals(response.jsonPath().get("booking.totalprice"), jsonPayload.getTotalprice());

    }

    @Test(priority = 3)
    public void UpdateBooking() {
        baseURI = "https://restful-booker.herokuapp.com";
        CreateResourceData jsonPayload = new CreateResourceData();
        jsonPayload.firstname = "Jim";
        jsonPayload.lastname = "Brown";
        jsonPayload.totalprice = 111;
        jsonPayload.depositpaid = true;
        BookingDatesData bookingDates = new BookingDatesData();
        jsonPayload.bookingdates = bookingDates;
        bookingDates.checkin = "2018-01-01";
        bookingDates.checkout = "2018-01-01";
        jsonPayload.additionalneeds = "Breakfast";
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonPayload);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        response = given().
                header("Content-Type", contentType).
                body(jsonString).log().all().
                when().
                put("/booking/8079/");
        System.out.println("The response code is : " + response.getStatusCode());
        System.out.println("The response body is as below : ");
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().get("booking.firstname"), jsonPayload.getFirstname());
        Assert.assertEquals(response.jsonPath().get("booking.lastname"), jsonPayload.getLastname());
        Assert.assertEquals(response.jsonPath().get("booking.totalprice"), jsonPayload.getTotalprice());
        // Assert.assertEquals(response.jsonPath().get("deposit"), jsonPayload.depositpaid);
    }
}