package first;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import file.ReusableMethods;
import file.payload;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		System.out.println("Jai ");
//		RestAssured.baseURI="https://rahulshettyacademy.com";
//		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
//		.body(payload.AddPlace()).when().post("maps/api/place/add/json")
//		.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
//		.header("Server", "Apache/2.4.52 (Ubuntu)");

//		AddPlace->update Place with new address->get place to validate if new address is present in response

		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(payload.AddPlace()).when().post("maps/api/place/add/json").then().assertThat().statusCode(200)
				.body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)").extract().response()
				.asString();

		System.out.println(response);

		JsonPath js = new JsonPath(response);
		String placeid = js.getString("place_id");
		System.out.println(placeid);

//		UpdatePlace
		String newAddr = "Hinjewadi,Pune";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body("{\r\n" + "\"place_id\":\"" + placeid + "\",\r\n" + "\"address\":\"" + newAddr + "\",\r\n"
						+ "\"key\":\"qaclick123\"\r\n" + "}")
				.when().put("maps/api/place/update/json").then().assertThat().statusCode(200)
				.body("msg", equalTo("Address successfully updated"));

//	GetPlace
		String getplaceResp = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeid).when()
				.get("maps/api/place/get/json").then().assertThat().log().all().statusCode(200).extract().response()
				.asString();

		JsonPath js1 = ReusableMethods.rawtoJson(getplaceResp);
		String actualAddr = js1.getString("address");
		System.out.println(actualAddr);
		Assert.assertEquals(actualAddr, newAddr);
	}

}
