import static org.junit.Assert.*;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

public class OzgurTest {

	@Test
	public void testValueTable() {
		Ozgur obj =new Ozgur();
		String[] data = new String[2];
		data[0] = "2010";
		data[1] = "Leonardo DiCaprio";
		ArrayList<String> values = new ArrayList<String>();
		values.add("Inception");
		values.add("Shutter Island");
		String expected ="<form><br>\n"
				+ "<table>\n"
				+ "<tr>\n"
				+ "<th>    Result   </th><th><b></b></th>\n"
				+ "<tr><td><input type=\"checkbox\" name=\"check0\" value=\"yes\"></td><td>Inception</td></tr><tr><td><input type=\"checkbox\" name=\"check1\" value=\"yes\"></td><td>Shutter Island</td></tr></table>\n"
						+ "<input type=\"submit\" name =\"Save\" value=\"yes\"><br><input type=\"hidden\" name =\"type\" value=\"actorYear\"><br></form><br>";
		assertEquals(expected, obj.valueTable(data, values, "actorYear", 3));
	}

	@Test
	public void testPageHeadAndInput() {
		Ozgur obj =new Ozgur();
		
		String expected = " <form>\nEnter Actor to search:<br>\n"+
				"<input type=\"text\" name=\"actorName\"><br>\n"+
				"<input type=\"text\" name=\"year\"><br>\n"+
				"<input type=\"submit\" name=\"gotValue\" value=\"yes\" <br>"+
				"<input type=\"hidden\" name=\"type\" value=\"actorYear\">"+
				"</form> <br><br><br>"
				+ "<p><a href=Ozgur?type=def>Go Back To Main Page<a></p><br>";
		
		assertEquals(expected, obj.pageHeadAndInput(3));
	}

	@Test
	public void testParseJSONarray() {
		JSONObject jsonObj = new JSONObject();
		JSONObject jsonObj2 = new JSONObject();
		JSONArray theArr = new JSONArray();
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("Ozgur");
		expected.add("Akyazi");
		expected.add("2012400");
		expected.add("Ready?");
		expected.add("yet");
		for(int i=0; i< 5; i++){
			jsonObj = new JSONObject();
			jsonObj2 = new JSONObject();
			
			jsonObj2.accumulate("value", expected.get(i));
			jsonObj.accumulate("itemLabel", jsonObj2);
			theArr.put(jsonObj);
		}
		
		Ozgur oscar = new Ozgur();
		assertEquals(expected, oscar.parseJSONarray(theArr));
	}

	@Test
	public void testHtmlStartCode() {
		String expected = " <!DOCTYPE html><html><body>";
		Ozgur oscar = new Ozgur();
		assertEquals(expected, oscar.htmlStartCode());
	}

	@Test
	public void testHtmlEndCode() {
		String expected = "</body></html>";
		Ozgur oscar = new Ozgur();
		assertEquals(expected, oscar.htmlEndCode());
	}

}
