
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class Ozgur_deneme
 */
@WebServlet("/Ozgur")
public class Ozgur extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public Ozgur_HtmlToJson wikiQ;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Ozgur() {
        super();
        System.out.println();
        wikiQ = new Ozgur_HtmlToJson();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter theo = response.getWriter();
		theo.println("My name is Özgür.");
		String[] thestr = new String[2];
		thestr[0] = "2010";
		thestr[0] = "Christian Bale";
		JSONArray theaArr = queryWiki(1,thestr);
		//String thefour = theaArr.getJSONObject(4).getJSONObject("countryLabel").getString("value");
		
		theo.append(indexPage());
		theo.append(theaArr.toString());
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	protected String indexPage(){
		String result = " <form>\nEnter to search:<br>\n"+
				"<input type=\"text\" name=\"firstname\"><br>\n"+
				"<input type=\"submit\" name=\"submit <br>\">"+
				"</form> ";
		return result;
	}
	
	/*
	 * Used to query wikidata, by using Ozgur_HtmlToJson class
	 * @typeOfQuery if 1 -> Query the films that the actor specified in the @data[0] played in.
	 * if 2 -> Query the actors in the film @data[0]
	 * if 3 -> Query the films in year @data[0] played by actor @data[1]
	 * */
	protected JSONArray queryWiki(int typeOfQuery, String[] data) {
		
		if(typeOfQuery == 1){
			return wikiQ.queryWithActor(data[0]);
		}
		else if(typeOfQuery ==2){
			return wikiQ.queryWithFilm(data[0]);
		}
		else if(typeOfQuery == 3){
			return wikiQ.queryWithActorAndYear(data[0], data[1]);
		}
		return null;
	}

}
