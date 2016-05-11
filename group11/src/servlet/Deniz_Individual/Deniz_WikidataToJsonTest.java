import static org.junit.Assert.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

/**
 * JUnit tester class for WikiData api query function.
 * 
 * @author Deniz Celik
 * @version 1.0
 * @since 05102016
 *
 */
public class Deniz_WikidataToJsonTest {

	@Test
	public void testQueryTeams() {
		JSONArray testArray = new JSONArray();
		testArray = Deniz_WikidataToJson.queryTeams();
		for (Object dataObj : testArray) {
			JSONObject tempJSON = (JSONObject) dataObj;
			assertNotNull(tempJSON.getJSONObject("teamLabel").get("value"));
		}
	}

}
