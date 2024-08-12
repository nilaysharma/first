package file;

import io.restassured.path.json.JsonPath;

public class ReusableMethods {

	public static JsonPath rawtoJson(String resp) {

		JsonPath js1 = new JsonPath(resp);
		return js1;
	}
}
