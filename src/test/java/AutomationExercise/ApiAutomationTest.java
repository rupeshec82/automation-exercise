package AutomationExercise;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ApiAutomationTest {

	@Test
	public void automationExercise2() {
		String URL = "https://restful-booker.herokuapp.com/booking";

		// Create booking
		Response response = given().contentType(ContentType.JSON)
				.body("{\"firstname\": \"Jim\"," + "\"lastname\": \"Brown\"," + "\"totalprice\": 111,"
						+ "\"depositpaid\": true," + "\"bookingdates\": {" + "\"checkin\": \"2018-01-01\","
						+ "\"checkout\": \"2019-01-01\"" + "}," + "\"additionalneeds\": \"Breakfast\"" + "}")
				.when().post(URL);

		System.out.println("Created Booking:\n" + response.asPrettyString());

		// Get the bookingid
		int bookingid = response.path("bookingid");
		System.out.println("Booking Id: " + bookingid);

		// Get the booking
		response = given().get(URL + "/" + bookingid);
		System.out.println("Get Booking:\n" + response.asPrettyString());

		// Update the booking
		response = given().auth().preemptive().basic("admin", "password123").contentType(ContentType.JSON)
				.body("{\"firstname\": \"James\"," + "\"lastname\": \"Brown\"," + "\"totalprice\": 111,"
						+ "\"depositpaid\": true," + "\"bookingdates\": {" + "\"checkin\": \"2018-01-01\","
						+ "\"checkout\": \"2019-03-06\"" + "}," + "\"additionalneeds\": \"Lunch\"" + "}")
				.when().put(URL + "/" + bookingid);

		System.out.println("Updated Booking:\n" + response.asPrettyString());
	}
}
