package servlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class Ozgur_HtmlToJson {
	
	final private static String base = "https://query.wikidata.org/sparql?query=";
	final static String basicQuery = "SELECT%20?countryLabel%20?capitalLabel%20WHERE{%20?capital%20wdt:P1376%20?country.%20?country%20wdt:P31%20wd:Q3624078.%20SERVICE%20wikibase:label%20{bd:serviceParam%20wikibase:language%20%22en%22%20.}}&format=json";
	
	public static JSONArray getJSONResponseForQuery(String theQuery){
		theQuery.replace("\"", "'");
		theQuery.replace(" ", "%20");
		theQuery.replace("'", "%22");
		
		return getJSONResponseURL(base + theQuery);
	}
	
	public static JSONArray getJSONResponseURL(String url) {
        
        String inputLine,myString;
        myString="";
        
        try {
        	URL oracle = new URL(url);
        	BufferedReader in = new BufferedReader(
        	        new InputStreamReader(oracle.openStream()));
        	while ((inputLine = in.readLine()) != null)
            	myString += (inputLine + "\n");
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(myString);
        JSONObject myJSONobject = new JSONObject(myString);
		JSONObject myJSONobject2 = myJSONobject.getJSONObject("results");
		JSONArray myJSONarray = myJSONobject2.getJSONArray("bindings");
		return myJSONarray;
	}
}