
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
 * Servlet implementation class Mustafa
 * Main page
 */
@WebServlet("/Mustafa")

public class Mustafa extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    public Mustafa() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("<body>");
		
		out.println("<h3>Population of countries</h3>");
		out.println("<p>Guess a population for countries. It will find the closest population for a country.</p>");
		out.println("<p><a href=Mustafa_Init>Initialize or refresh the database from wikidata<a></p>");
		out.println("<p><a href=Mustafa_Input>Make a query for population<a></p>");
		out.println("<p><a href=Mustafa_Saved>Saved countries<a></p>");

		out.println("</body>");
		out.println("</html>");
	}	
}
