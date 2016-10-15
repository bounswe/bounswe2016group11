
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
 * Servlet implementation class Mustafa_Init
 * Initializes or refreshes the database
 */
@WebServlet("/Mustafa_Init")
public class Mustafa_Init extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Mustafa_Init() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		Mustafa_DatabaseConnection.dropDatabase();
		Mustafa_DatabaseConnection.createDatabase();
		Mustafa_DatabaseConnection.useDatabase();
		Mustafa_DatabaseConnection.createTable();
		JSONArray wikiJSON = new JSONArray();
		String query = "SELECT%20?populationLabel%20?countryLabel%20WHERE{%20?country%20wdt:P1082%20?population.%20?country%20wdt:P31%20wd:Q3624078.%20SERVICE%20wikibase:label%20{%20bd:serviceParam%20wikibase:language%20%27en%27%20.%20}%20}&format=json";
		try {
			wikiJSON = Mustafa_Wikidata.getHtml(query);		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 0; i<wikiJSON.length();i++){
			JSONObject myObject = wikiJSON.getJSONObject(i);
			String mycountry = myObject.getJSONObject("countryLabel").getString("value");
			Integer mypopulation = Integer.parseInt(myObject.getJSONObject("populationLabel").getString("value"));
			Mustafa_DatabaseConnection.addData(new Mustafa_Data(mycountry,mypopulation));
		}
		out.println("<p><a href=Mustafa>Go back!<a></p>");
		out.println("<p>Successful! Database is initialized or refreshed</p>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
