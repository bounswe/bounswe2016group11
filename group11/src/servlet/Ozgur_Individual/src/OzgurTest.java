import static org.junit.Assert.*;

import java.util.ArrayList;

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
		fail("Not yet implemented");
	}

	@Test
	public void testHtmlStartCode() {
		fail("Not yet implemented");
	}

	@Test
	public void testHtmlEndCode() {
		fail("Not yet implemented");
	}

}
