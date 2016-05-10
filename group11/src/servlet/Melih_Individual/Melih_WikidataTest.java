import static org.junit.Assert.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.*;

public class Melih_WikidataTest {

	@Test
	public void testGetHtml() {
		JSONArray myJSONarray = new JSONArray();
		try {
			myJSONarray = Melih_Wikidata.getHtml("https://query.wikidata.org/sparql?query=SELECT%20?emperorLabel%20?dateLabel%20WHERE%20{%20?emperor%20wdt:P31%20wd:Q5%20.%20?emperor%20p:P39%20?position_held_statement%20.%20?position_held_statement%20ps:P39%20wd:Q842606.%20?position_held_statement%20pq:P580%20?date%20.%20SERVICE%20wikibase:label%20{%20bd:serviceParam%20wikibase:language%20%27en%27%20.%20}%20}%20ORDER%20BY%20?date&format=json");
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(int i = 0; i<myJSONarray.length();i++){
			JSONObject tempRecord = myJSONarray.getJSONObject(i);
			String tempEmperor = tempRecord.getJSONObject("emperorLabel").getString("value");
			assertNotNull(tempEmperor);
		}
	}
}


