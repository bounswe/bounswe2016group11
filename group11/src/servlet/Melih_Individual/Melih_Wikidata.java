import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

/**
* This servlet is the Wikidata communication file for the project Roman 
* Emperors and their Years of Ascension. 
*
* In creation of this class, codes and instruction in 
* the following link have been partially utilized: 
* https://docs.oracle.com/javase/tutorial/networking/urls/readingURL.html
*
* @author  Melih Barsbey
* @version 1.0
* @since   2016-05-10 
*/

public class Melih_Wikidata {
	/**
	* This method makes a request to wikidata server, using the query provided
	* by the caller method. The request is in JSON format, thus, the response
	* of the server is decoded to be a simple JSON array containing only the
	* results, and is returned to the caller.
	*
	* @param query Query to be made to the wikidata server.
	* @return myJSONarray The result array within the JSON response of the Wikidata server.
	*/
	
	public static JSONArray getHtml(String query) throws Exception {
        URL oracle = new URL(query);
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
