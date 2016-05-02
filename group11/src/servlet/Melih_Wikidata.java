package net.codejva;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Melih_Wikidata {
	
	final static String base = "https://query.wikidata.org/sparql?query=";
	final static String query = "SELECT%20?h%20WHERE{?h%20wdt:P31%20wd:Q3624078}";
	
	public static String getHtml() throws Exception {
		String url = base + query;
        URL oracle = new URL(url);
        BufferedReader in = new BufferedReader(
        new InputStreamReader(oracle.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();
		return inputLine;
	}
}
