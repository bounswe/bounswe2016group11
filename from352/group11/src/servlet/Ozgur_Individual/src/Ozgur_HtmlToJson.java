import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class Ozgur_HtmlToJson {
	
	final private  String base = "https://query.wikidata.org/sparql?query=";
	final String queryActor = "SELECT ?itemLabel WHERE { "
			+ "?item wdt:P31 wd:Q11424. "
			+ "?actor wdt:P106 wd:Q33999 ."
			+ " ?item wdt:P161 ?actor. "
			+ "?actor rdfs:label  \"theActor\"@en. "
			+ "SERVICE wikibase:label { bd:serviceParam wikibase:language \"en\" }}";
	final String queryActorYear = "SELECT ?itemLabel WHERE "
			+ "{ ?item wdt:P577 ?date FILTER( ?date "
			+ "= \"theYear-01-01T00:00:00Z\"^^xsd:dateTime). ?item wdt:P31 wd:Q11424 . "
			+ "?item wdt:P161 ?person."
			+ " ?person wdt:P31 wd:Q5. "
			+ "?person rdfs:label  ?name filter(?name = \"theActor\"@en). "
			+ "SERVICE wikibase:label { bd:serviceParam wikibase:language \"en\" }}";
	final String queryFilm = "SELECT %3FitemLabel "
			+ "WHERE { %3Ffilm wdt%3AP31 wd%3AQ11424. "
			+ "%3Ffilm rdfs%3Alabel  \"theFilm\"%40en. "
			+ "%3Ffilm wdt%3AP161 %3Fitem. "
			+ "%3Fitem wdt%3AP106 wd%3AQ33999 .  "
			+ "SERVICE wikibase%3Alabel { bd%3AserviceParam wikibase%3Alanguage \"en\" } }";
	
	/*
	 * @theQuery query for wikidata starting with a SELECT ... statement.
	 * To make it consistent with a web link, some characters in it are changed 
	 * accordingly i.e. : --> %3A
	 *  				= --> %3D
	 *  				....
	 *  
	 *   please the function for the res
	 * */
	private JSONArray getJSONResponseForQuery(String theQuery){
		theQuery = theQuery.replace(":", "%3A");
		theQuery = theQuery.replace("=", "%3D");
		theQuery = theQuery.replace("@", "%40");
		theQuery = theQuery.replace("?", "%3F");
		theQuery = theQuery.replace(" ", "%20");
		
		System.out.println(base + theQuery);
		return getJSONResponseURL(base + theQuery + "%20&format=json");
	}
	
	/*
	 * Queries the wikidata database for the films in which the actor named @actor played, and 
	 * the publication data of the film is equal to @year
	 * 
	 * @year The publication of the films queried
	 * @actor The actor who has played in the resulting films
	 * 
	 * */
	public JSONArray queryWithActorAndYear(String year, String actor){
		String theQuery = queryActorYear.replace("theYear", year).replace("theActor", actor);
		return getJSONResponseForQuery(theQuery);
	}
	
	/*
	 * Queries the wikidata for the actors playing in the 
	 * film whose name specified with input @film
	 * 
	 * @film the film name whose actors to be queried
	 * 
	 * */
	public JSONArray queryWithFilm(String film){
		String theQuery = queryFilm.replace("theFilm", film);
		return getJSONResponseForQuery(theQuery);
	}
	/*
	 * Queries the wikidata for the films of the actor specified with input @actor
	 * 
	 * @actor the actor whose films to be queried
	 * 
	 * */
	public JSONArray queryWithActor(String actor){
		String theQuery = queryActor.replace("theActor", actor);
		return getJSONResponseForQuery(theQuery);
	}
	
	/*
	 * Returns the JSONArray which is got from the @url URL.
	 * However, because it is known that the query is asked to wikidata
	 * and in the response the value is in the JSONArray of "bindings" 
	 * which is in the JSONObject named "results", the JSONArray is the 
	 * JSONArray of "bindings"
	 * 
	 * 
	 * Also Main structure of the connection to wikidata 
	 * page was taken from https://docs.oracle.com/javase/tutorial/networking/urls/readingURL.html 
	 * 
	 * @url the URL to fetch the json file
	 * 
	 * */
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
			return null;
		}
        System.out.println(myString);
        JSONObject myJSONobject = new JSONObject(myString);
		JSONObject myJSONobject2 = myJSONobject.getJSONObject("results");
		JSONArray myJSONarray = myJSONobject2.getJSONArray("bindings");
		return myJSONarray;
	}
}