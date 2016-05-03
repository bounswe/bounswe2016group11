package net.codejva;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class Melih_Wikidata {
	
	final static String base = "https://query.wikidata.org/sparql?query=";
	//final static String query = "SELECT%20?countryLabel%20?capitalLabel%20WHERE{%20?capital%20wdt:P1376%20?country.%20?country%20wdt:P31%20wd:Q3624078.%20SERVICE%20wikibase:label%20{bd:serviceParam%20wikibase:language%20%22en%22%20.}}&format=json";
	
	
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
