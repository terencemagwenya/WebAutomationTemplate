package mfstestcases;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Q2ApiTesting {
    static Response response;
    static final String contentType = "application/json; charset=UTF-8";
    static final String projectFilePath = System.getProperty("user.dir");
    static final String jsonPayloadFilePath = projectFilePath + "\\jsonfiles\\";
    public static Properties properties;
    private static FileInputStream fileInputStream = null;
    private static Integer bookingId = 0;

    @BeforeClass
    void beforeClass() {
        properties = new Properties();

        try {
            fileInputStream = new FileInputStream("src/test/java/utils/config.properties");
        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            properties.load(fileInputStream);
        } catch (
                IOException e) {
            e.printStackTrace();
        }

    }

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
        bookingId = response.jsonPath().get("bookingid");
        Assert.assertEquals(response.jsonPath().get("booking.firstname"), jsonPayload.getFirstname());
        Assert.assertEquals(response.jsonPath().get("booking.lastname"), jsonPayload.getLastname());
        Assert.assertEquals(response.jsonPath().get("booking.totalprice"), jsonPayload.getTotalprice());
        System.out.println("Booking id is " + bookingId);

    }

    @Test(priority = 2)
    public void GetBooking() {
        baseURI = "https://restful-booker.herokuapp.com";
        response = given().
                get("/booking/" + bookingId + "/");
        System.out.println("Get Booking Response\n");
        System.out.println("The response code is : " + response.getStatusCode());
        System.out.println("The response body is as below : ");
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 3)
    public void UpdateBooking() {
        if (bookingId == 0) {
            bookingId = 2535;
        }
        baseURI = "https://restful-booker.herokuapp.com";
        CreateResourceData jsonPayload = new CreateResourceData();
        jsonPayload.firstname = "Logan";
        jsonPayload.lastname = "Magwenya";
        jsonPayload.totalprice = 10000;
        jsonPayload.depositpaid = true;
        BookingDatesData bookingDates = new BookingDatesData();
        jsonPayload.bookingdates = bookingDates;
        bookingDates.checkin = "2022-12-01";
        bookingDates.checkout = "2022-12-30";
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
                auth().preemptive().basic(properties.getProperty("basicUsername"), properties.getProperty("basicPassword")).
                cookie(properties.getProperty("tokenKey"), properties.getProperty("tokenValue")).
                body(jsonString).log().all().
                when().
                put("/booking/" + bookingId + "/");
        System.out.println("The response code is : " + response.getStatusCode());
        System.out.println("The response body is as below : ");
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().get("firstname"), jsonPayload.getFirstname());
        Assert.assertEquals(response.jsonPath().get("lastname"), jsonPayload.getLastname());
        Assert.assertEquals(response.jsonPath().get("totalprice"), jsonPayload.getTotalprice());
        Assert.assertEquals(response.jsonPath().get("depositpaid"), jsonPayload.depositpaid);
        Assert.assertEquals(response.jsonPath().get("bookingdates.checkin"), bookingDates.checkin);
        Assert.assertEquals(response.jsonPath().get("bookingdates.checkout"), bookingDates.checkout);
        Assert.assertEquals(response.jsonPath().get("additionalneeds"), jsonPayload.additionalneeds);
        System.out.println(bookingId);
    }
}