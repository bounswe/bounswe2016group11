import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;


public class Deniz_WikidataToJson {
	
	final private String base = 
			"https://query.wikidata.org/sparql?query=";
	final private String queryAll = 
			"SELECT ?teamLabel ?yearLabel ?countryLabel "
			+ "WHERE{ "
			+ "?team wdt:P31 wd:Q476028 . "
			+ "?team wdt:P571 ?year . "
			+ "?team wdt:P17 ?country . "
			+ "?country wdt:P30 wd:Q49 . "
			+ "SERVICE wikibase:label {bd:serviceParam wikibase:language \"en\" .}}";
	
	private String rawStringToQuery(String rawString){
		String tempQuery = rawString.replace(":", "%3A");
		tempQuery = tempQuery.replace("=", "%3D");
		tempQuery = tempQuery.replace("@", "%40");
		tempQuery = tempQuery.replace("?", "%3F");
		tempQuery = tempQuery.replace(" ", "%20");
		return base+tempQuery+"%20&format=json";
	}
	
	private JSONArray doQuery(String rawQuery){
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
        System.out.println(myString);
        JSONObject originalJSON = new JSONObject(myString);
		JSONObject resultsJSON = originalJSON.getJSONObject("results");
		JSONArray bindingsJSON = resultsJSON.getJSONArray("bindings");
		return bindingsJSON;
	}
	
	public JSONArray queryTeams(){
		return doQuery(queryAll);
	}
}
