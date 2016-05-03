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
		String selection = request.getParameter("menu");
		out.println("<html>");
		out.println("<body>");
		if(selection == null || selection.equals("main")){
			out.println("<p><a href=Melih?menu=makequery>Make a query<a></p>");
			out.println("<p><a href=Melih?menu=flush>Flush the database and refurbish it from the Wikidat<a></p>");
			out.println("<p><a href=Melih?menu=database>Show contents of the database<a></p>");
		} else if (selection.equals("makequery")){
			out.println("<form action='Melih' method='get'>Make a query:<br><input type='text' name='my_query'><br><br><input type='submit' value='Submit'></form>");
			out.println("<p><a href=Melih?menu=main>Go to main menu<a></p>");
		} else if (selection.equals("flush")){
			Melih_DatabaseConnection.dropDatabase();
			Melih_DatabaseConnection.createDatabase();
			//Melih_DatabaseConnection.useDatabase();
			Melih_DatabaseConnection.createTable();
			JSONArray myJSONarray = new JSONArray();
			String query = "SELECT%20?emperorLabel%20?dateLabel%20WHERE%20{%20?emperor%20wdt:P31%20wd:Q5%20.%20?emperor%20p:P39%20?position_held_statement%20.%20?position_held_statement%20ps:P39%20wd:Q842606.%20?position_held_statement%20pq:P580%20?date%20.%20SERVICE%20wikibase:label%20{%20bd:serviceParam%20wikibase:language%20%27en%27%20.%20}%20}%20ORDER%20BY%20?date&format=json";
			String my_input ="";
			try {
				myJSONarray = Melih_Wikidata.getHtml(query);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for(int i = 0; i<myJSONarray.length();i++){
				JSONObject tempRecord = myJSONarray.getJSONObject(i);
				String tempEmperor = tempRecord.getJSONObject("emperorLabel").getString("value");
				String tempDate = tempRecord.getJSONObject("dateLabel").getString("value");
				Integer intDate = Integer.parseInt(tempDate.substring(0,4));
				Melih_DatabaseConnection.addData(new Melih_Data(tempEmperor,intDate));
			}
			out.println("<p><a href=Melih?menu=main>Go to main menu<a></p>");
		} else if (selection.equals("database")){
			ArrayList<Melih_Data> myData = Melih_DatabaseConnection.getData();
			
			out.println(myData.get(3).emperor);
			out.println("<p><a href=Melih?menu=main>Go to main menu<a></p>");
		}
		//else{out.println("<a href = Melih?menu=whatever>Click here?</a>");}	
		
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
		//out.println("son");
		//System.out.println(my_input);
		
		//out.println("<form action='https://query.wikidata.org/sparql?query=SELECT ?countryLabel ?capitalLabel WHERE {?country wdt:P31 wd:Q3624078. ?capital wdt:P1376 ?country. SERVICE wikibase:label{bd:serviceParam wikibase:language 'en'. }}' method='get'> Click below to send your query:<input type='submit' value='Submit'></form>");
		//out.println("<br>");
		//out.println("<form action='Melih' method='get'> Please click here to flush and reinitialize the database: <input type='submit' value='Click'></form>");
		out.println("</body>");
		out.println("</html>");
	}	
}

//JSONObject tempRecord = myJSONarray.getJSONObject(10);
//String dene = tempRecord.getJSONObject("emperorLabel").getString("value");
//System.out.println(dene);
