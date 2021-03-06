
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
/**
 * Common class that handles wikidata connection
 * Reference: https://docs.oracle.com/javase/tutorial/networking/urls/readingURL.html
 * Reference date: 03.05.2016
 * @author Mustafa
 *
 */
public class Mustafa_Wikidata {
	
	final static String base = "https://query.wikidata.org/sparql?query=";
	
	public static JSONArray getHtml(String query) throws Exception {
		 
		String url = base + query;
        URL oracle = new URL(url);
        BufferedReader in = new BufferedReader(
        new InputStreamReader(oracle.openStream()));
        String inputLine,myString;
        myString="";
        while ((inputLine = in.readLine()) != null)
        	myString += (inputLine + "\n");
        in.close();
        JSONObject myJSONobject = new JSONObject(myString);
		JSONObject myJSONobject2 = myJSONobject.getJSONObject("results");
		JSONArray myJSONarray = myJSONobject2.getJSONArray("bindings");
		return myJSONarray;
	}
}