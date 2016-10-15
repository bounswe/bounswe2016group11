

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
 * Servlet implementation class Dogukan
 */
@WebServlet("/Dogukan")
public class Dogukan extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Dogukan() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pTool = response.getWriter();
		String selection = request.getParameter("type");
		pTool.println("<html>");
		pTool.println("<head><title>352</title></head>");
		pTool.println("<body>");
		pTool.println("<h2>ALL STADIUMS AROUND THE WORLD!</h2>");
		pTool.println("<p><a href=Dogukan?type=initialize>Initialize the database</a></p>");
		pTool.println("<p><a href=Dogukan?type=stadium>Get a stadium</a> (enter a name of stadium to see its features)</p>");

		if (selection.equals("stadium")){
			pTool.println("<form action='Dogukan' method='get'>Type the team name:<br><input type='hidden' name='type' value='query'/><input type='text' name='input'><br><br><input type='submit' value='Submit'></form>");
			pTool.println("<p><a href=Dogukan?type=home>Home<a></p>");
		}
		if (selection.equals("initialize")){
			Dogukan_DatabaseConnection.removeData();
			JSONArray jsonResult = new JSONArray();
			String stadiumQuery = "SELECT%20%3FstadiumLabel%20%3FteamLabel%0AWHERE%0A%7B%0A%09%3Fstadium%20wdt%3AP31%20wd%3AQ483110%20.%0A%09%3Fstadium%20wdt%3AP127%20%3Fteam%20.%0A%09SERVICE%20wikibase%3Alabel%20%7B%0A%09%09bd%3AserviceParam%20wikibase%3Alanguage%20%22en%22%20.%0A%09%7D%0A%7D&format=json";
			try {
				jsonResult = Dogukan_Wikidata.getHtml(stadiumQuery);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(int i = 0; i<jsonResult.length();i++){
				JSONObject tempRecord = jsonResult.getJSONObject(i);
				String tempStadium = tempRecord.getJSONObject("stadiumLabel").getString("value");
				String tempTeam = tempRecord.getJSONObject("teamLabel").getString("value");
				Dogukan_DatabaseConnection.addData(tempStadium, tempTeam, false);
			}
			pTool.println("<p>Database is up!</p>");
			pTool.println("<p><a href=Dogukan?type=home>home<a></p>");
		}
		if (selection.equals("query")) {
			String teamName = request.getParameter("input");
			ArrayList<Dogukan_Data> stadiumData = Dogukan_DatabaseConnection.makeQuery(teamName);
			pTool.println("<p>" + stadiumData.size() + "stadium found!</p><br>");
			if (stadiumData.size() > 0) {
				pTool.println("<table>");
				pTool.println("<tr>");
				pTool.println("<th><strong>Team</strong></th>");
				pTool.println("<th><strong>Stadium</strong></th>");
				pTool.println("</tr>");
				for(int i = 0; i < stadiumData.size();i++){
					
					pTool.println("<tr>");
					pTool.println("<td align='center'>"+ stadiumData.get(i).team +"</td>");
					pTool.println("<td align='center'>"+ stadiumData.get(i).stadium +"</td>");
					pTool.println("</tr>");
				}
				pTool.println("</table><br>");
				pTool.println("<p><a href=Dogukan?type=home>Home<a></p>");
			}
			else {
				pTool.println("no stadium with that team name");
				pTool.println("<p><a href=Dogukan?type=home>Home<a></p>");
			}
		}
		
		
		pTool.println("</body>");
		pTool.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	

}
