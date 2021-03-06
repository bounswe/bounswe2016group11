

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class Ozgur
 */
@WebServlet("/Ozgur")
public class Ozgur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public ArrayList<String> lastFetched = new ArrayList<String>();
	public Ozgur_HtmlToJson wikiQ;
	public String[] lastQuery = new String[2];
	public int lastQueryType;
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
		Ozgur_DB.useDatabase();
		theo.println(htmlStartCode());
		checkAndAction(theo, request);
		theo.println(htmlEndCode());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/*
	 * Checks the query type that the user has selected and puts the 
	 * appropriate results into the screen
	 * 
	 * @theo the response to the HttpServlet
	 * @request the information about the page to check its state and get action accordingly 
	 * */
	protected void checkAndAction(PrintWriter theo, HttpServletRequest request){
		int type=0;
		String queryType = request.getParameter("type");
		String gotValue = request.getParameter("gotValue");
		ArrayList<String> theResult = null;
		if(queryType == null || queryType.equals("def")){//if default or not set
			theo.println("<h1>Welcome To �zg�r's Project</h1><br>");
			theo.println("<p>In this project you can search about films and actors</p><br>");
			theo.println("<p><b>Here are your choices:</b></p><br>");
			theo.println(pageHeadAndInput(0));
			return;
		}
		else if(gotValue != null ){
			if(gotValue.equals("yes")){//if we have an input to query then initialize theResult
				theResult = new ArrayList<String>();
			}

		}


		if(queryType.equals("actor")){// if it is actor type
			theo.println(pageHeadAndInput(1));
			type = 1;
		}
		else if(queryType.equals("film")){// query by film
			theo.println(pageHeadAndInput(2));
			theo.println();theo.println();theo.println();
			type = 2;
		}
		else if(queryType.equals("actorYear")){//query by actor and year
			theo.println(pageHeadAndInput(3));
			type = 3;
		}
		lastQueryType = type;
		JSONArray jsonArr = new JSONArray();
		String[] data = new String[2];
		if(theResult != null){
			System.out.println("Here  " + type);
			String toWord = "";
			switch (type) {
			case 1:
				String actor = request.getParameter("actorName");
				data[0] = actor;
				toWord = "The films that " + actor+ " played are:<br><br><br>";
				jsonArr =  queryWiki(1,data);
				break;
			case 2:
				String film = request.getParameter("filmName");
				data[0] = film;
				toWord = "The actors played in  the movie " + film + " are:<br><br><br>";
				jsonArr =  queryWiki(2,data);
				break;
			case 3:
				actor = request.getParameter("actorName");
				String year = request.getParameter("year");
				data[0] = year;
				data[1] = actor;
				toWord = "The films that " + actor + " played in year: " +year + " are:<br><br><br>";
				jsonArr =  queryWiki(3,data);
				break;

			default:
				break;

			}
			
			lastQuery = data;
			theo.println(toWord);
			theResult = parseJSONarray(jsonArr);
			lastFetched = theResult;
			theo.println(valueTable(data, theResult, queryType,type));
		}
		else if(request.getParameter("Save") != null){
			
			if(request.getParameter("Save").equals("yes")){
				ArrayList<String> toInsert = new ArrayList<String>();

				for(int i=0; i<lastFetched.size(); i++){
					if(request.getParameter(("check"+i)) != null){
						if(request.getParameter(("check" + i)).equals("yes")){
							toInsert.add(lastFetched.get(i));
						}
					}
				}

				Ozgur_DB.addData(toInsert, lastQueryType, lastQuery);
			}
		}
	}
	/*
	 * Returns the result of the query with a table format in HTML.
	 * @query query[0] is actor name or film name if @type="actor" or "film".
	 *  if @type="actorYear" @query[0] is year and @query[1] is actor name
	 *  
	 *  @type  type of the query in string format
	 *  @intType type of the query in integer form
	 *  @value the results for the query COMING FROM wikidata, not the internal data
	 * 
	 * */
	protected String valueTable(String[] query, ArrayList<String> value,String type, int intType){
		if(value.size() == 0){
			return "";
		}
		String result = "<form><br>\n"
				+ "<table>\n"
				+ "<tr>\n"
				+ "<th>    Result   </th>"
				+ "<th><b></b></th>\n";
		ArrayList<String> prevSaved = Ozgur_DB.makeQuery(query, intType);
		value.removeAll(prevSaved);
		lastFetched = value;
		for(int i= 0 ; i<prevSaved.size(); i++){
			result += "<tr>"
					+ "<td><input type=\"checkbox\" name=\"checkPrev" + i +"\" value=\"yes\" checked></td>"
					+ "<td>" + prevSaved.get(i)+ "</td>"
					+ "</tr>";

		}
		for(int i= 0 ; i<value.size(); i++){
			result += "<tr>"
					+ "<td><input type=\"checkbox\" name=\"check" + i +"\" value=\"yes\"></td>"
					+ "<td>" + value.get(i)+ "</td>"
					+ "</tr>";

		}
		result +="</table>\n"
				+ "<input type=\"submit\" name =\"Save\" value=\"yes\"><br>"
				+ "<input type=\"hidden\" name =\"type\" value=\""+type+ "\"><br>"
				+"</form><br>";
		
		return result;
	}
	/*
	 * Returns the needed form as output according to input @typeOfQuery
	 * If @typeOfQuery -> 0 , form includes buttons to choose which type of query does the user want to have 
	 * If @typeOfQuery -> 1 , form includes just a field to get actor name
	 * If @typeOfQuery -> 2 , form includes just a field to get film name
	 * If @typeOfQuery -> 3 , form includes two fields to get the year of film
	 * and the name of the film.
	 * 
	 * @typeOfQuery the type of form to be returned
	 * */
	protected String pageHeadAndInput(int typeOfQuery){
		if(typeOfQuery == 0){
			String result = "<p><a href=Ozgur?type=actor>Query with actor name to see all the movies played by the actor!<a></p><br>";
			result += "<p><a href=Ozgur?type=film>Query with movie name to see actors played in that movie!<a></p><br>";
			result += "<p><a href=Ozgur?type=actorYear>Query with actor name and year to see all the movies played by the actor in that year<a></p><br>"
					+ "<p><b>Please note that you can see the previously saved choices when follow exactly same path<br>"
					+ "For example Query by actor name with Leonardo DiCaprio and save Shutter Island. <br>Then "
					+ "again, the query by actor name and Leonardo DiCaprio should be used as input.</b></p>";
			return result;
		}
		else if(typeOfQuery ==1){
			String result = " <form>\nEnter Actor to search:<br>\n"+
					"<input type=\"text\" name=\"actorName\"><br>\n"+
					"<input type=\"submit\" name=\"gotValue\" value=\"yes\" <br>"
					+ "<input type=\"hidden\" name=\"type\" value=\"actor\">"+
					"</form> <br><br><br>"
					+ "<p><a href=Ozgur?type=def>Go Back To Main Page<a></p><br>";
			return result;
		}
		else if(typeOfQuery ==2){
			String result = " <form>\nEnter Film to search:<br>\n"+
					"<input type=\"text\" name=\"filmName\"><br>\n"+
					"<input type=\"submit\" name=\"gotValue\" value=\"yes\"<br>"
					+ "<input type=\"hidden\" name=\"type\" value=\"film\">"+
					"</form> <br><br><br>"
					+ "<p><a href=Ozgur?type=def>Go Back To Main Page<a></p><br>";
			return result;
		}
		else if(typeOfQuery ==3){
			String result = " <form>\nEnter Actor to search:<br>\n"+
					"<input type=\"text\" name=\"actorName\"><br>\n"+
					"<input type=\"text\" name=\"year\"><br>\n"+
					"<input type=\"submit\" name=\"gotValue\" value=\"yes\" <br>"+
					"<input type=\"hidden\" name=\"type\" value=\"actorYear\">"+
					"</form> <br><br><br>"
					+ "<p><a href=Ozgur?type=def>Go Back To Main Page<a></p><br>";
			return result;
		}
		return "";
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

	/*
	 * Converts the JSONArray taken from wikidata into arraylist.
	 * 
	 * @jsonArr the JSONArray to be converted into ArrayList<String>
	 *
	 * */
	protected ArrayList<String> parseJSONarray(JSONArray jsonArr){
		ArrayList<String> result = new ArrayList<String>();
		for(int i=0 ; i<jsonArr.length(); i++){
			JSONObject theobj = jsonArr.getJSONObject(i).getJSONObject("itemLabel");
			String res = theobj.getString("value");
			System.out.println(res);
			result.add(res);
		}
		return result;
	}
	protected String htmlStartCode(){
		String s = " <!DOCTYPE html>"
				+ "<html>"
				+ "<body>";
		
		return s;
	}
	protected String htmlEndCode(){
		String s = "</body>"
				+ "</html>";
		
		return s;
	}
}
