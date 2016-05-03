package net.codejva;

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
 * Servlet implementation class Melih
 */
@WebServlet("/Melih")



public class Melih extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    public Melih() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String my_query = request.getParameter("menu");
		out.println("<html>");
		out.println("<body>");
		if(my_query == null){
			out.println("<form action='Melih' method='get'> Please enter a query:<br><input type='text' name='my_query'><br><br><input type='submit' value='Submit'></form>");
			out.println("<p><><></p>");
		}
		//else{out.println("<a href = Melih?menu=whatever>Click here?</a>");}	
		//out.println("<form action='Melih' method='get'> Please enter a query:<br><input type='text' name='my_query'><br><br><input type='submit' value='Submit'></form>");
		//out.println("<form action='Melih' method='get'> Please enter a query:<br><input type='text' name='my_query'><br><br><input type='submit' value='Submit'></form>");
		//else{out.println("<p>" + my_query + "</p>");}
		//out.println("<br>");
		//Melih_DatabaseConnection.addUser(new Melih_User(1, "root"));
//		out.println("<p style='font-color:red'>User got.</p>");
//		ArrayList<Melih_User> myUsers = Melih_DatabaseConnection.getUsers();
//		out.println("<table>");
//			out.println("<tr>");
//				out.println("<td>user_id</td>");
//				out.println("<td>user_name</td>");
//			out.println("</tr>");
//		for(int i=0; i<myUsers.size();i++ ){
//			out.println("<tr>");
//			out.println("<td>"+myUsers.get(i).user_id + "</td><td>" + myUsers.get(i).user_name+"</td>");
//			out.println("</tr>");
//		}
//		out.println("</table>");
		JSONArray myJSONa = new JSONArray();
		String my_input ="";
		try {
			myJSONa = Melih_Wikidata.getHtml();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JSONObject rec = myJSONa.getJSONObject(10);
	    String dene = rec.getJSONObject("countryLabel").getString("value");
		System.out.println(dene);
		//out.println("son");
		//System.out.println(my_input);
		
		//out.println("<form action='https://query.wikidata.org/sparql?query=SELECT ?countryLabel ?capitalLabel WHERE {?country wdt:P31 wd:Q3624078. ?capital wdt:P1376 ?country. SERVICE wikibase:label{bd:serviceParam wikibase:language 'en'. }}' method='get'> Click below to send your query:<input type='submit' value='Submit'></form>");
		//out.println("<br>");
		//out.println("<form action='Melih' method='get'> Please click here to flush and reinitialize the database: <input type='submit' value='Click'></form>");
		out.println("</body>");
		out.println("</html>");
	}	
}
