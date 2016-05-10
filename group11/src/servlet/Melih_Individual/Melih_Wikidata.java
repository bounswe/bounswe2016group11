import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

/**
* This class implements the query made to Wikidata, and its initial parsing 
* for the project Roman Emperors and their Years of Ascension. 
*
* In creation of this class, codes and instruction in the following link
* have been utilized with modifications and additions: 
* https://docs.oracle.com/javase/tutorial/networking/urls/readingURL.html
* The page was first accessed in 03.05.16. See below for Oracle's conditions
* of use.
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
	* @param query Query to be made to the Wikidata server.
	* @return myJSONarray The result array within the JSON response of the Wikidata server.
	* @throws Exception is thrown whenever the url action has not succeeded.
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

/*
Copyright (c) 2014, Oracle America, Inc.
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
Neither the name of Oracle nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
