import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Class for querying the WikiData API endpoint and returning
 * the results as a JSONArray. 
 * 
 * During the creation of this class, the following link was used
 * as a basis with modification and addition for project specific
 * methods:
 * 
 * https://docs.oracle.com/javase/tutorial/networking/urls/readingURL.html
 * See below for Oracle's conditions of use.
 * 
 * The site was first accessed on May 5th, 2016. 
 * 
 * @author Deniz Celik
 * @version 1.0
 * @since 05102016
 */
public class Deniz_WikidataToJson {
	//Base wikidata api string
	final static private String base = 
			"https://query.wikidata.org/sparql?query=";
	// Unique query for grabbing american soccer team data
	final static private String queryAll = 
			"SELECT ?teamLabel ?yearLabel "
			+ "WHERE{ "
			+ "?team wdt:P31 wd:Q476028 . "
			+ "?team wdt:P571 ?year . "
			+ "?team wdt:P17 ?country . "
			+ "?country wdt:P17 wd:Q30 . "
			+ "SERVICE wikibase:label {bd:serviceParam wikibase:language \"en\" .}}";
	
	/**
	 * Takes in a raw query string and converts it to
	 * an acceptable html getRequest.
	 * @param rawString WikiData query to clean and convert
	 * @return the WikiData query in an html getRequest format with base and settings added
	 */
	private static String rawStringToQuery(String rawString){
		String tempQuery = rawString.replace(":", "%3A");
		tempQuery = tempQuery.replace("=", "%3D");
		tempQuery = tempQuery.replace("@", "%40");
		tempQuery = tempQuery.replace("?", "%3F");
		tempQuery = tempQuery.replace(" ", "%20");
		return base+tempQuery+"%20&format=json";
	}
	
	/**
	* Requests a query from the WikiData endpoint using the provided query string. 
	* Upon successful response, the provided response is converted to the JSON format
	* for easier access and storage.
	*
	* @param rawQuery WikiData query to be made.
	* @return A JSONArray containing only the relevant WikiData information.
	*/
	private static JSONArray doQuery(String rawQuery){
		String currentQuery = rawStringToQuery(rawQuery);
        String inputLine,myString;
        myString="";
       
        try {
        	URL oracle = new URL(currentQuery);
        	BufferedReader in = new BufferedReader(
        	        new InputStreamReader(oracle.openStream()));
        	while ((inputLine = in.readLine()) != null)
            	myString += (inputLine + "\n");
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
//      System.out.println(myString);
        JSONObject originalJSON = new JSONObject(myString);
		JSONObject resultsJSON = originalJSON.getJSONObject("results");
		JSONArray bindingsJSON = resultsJSON.getJSONArray("bindings");
		return bindingsJSON;
	}
	
	/**
	 * Helper method that calls the query on our unique American Soccer Teams
	 * query.
	 * @return JSONArray of WikiData
	 */
	public static JSONArray queryTeams(){
		return doQuery(queryAll);
	}
}

/*
Copyright (c) 2014, Oracle America, Inc.
All rights reserved.
Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
Neither the name of Oracle nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */