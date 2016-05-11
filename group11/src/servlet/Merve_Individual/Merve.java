/**
 * This is the main code for the program which serves as HTML converter to the server. 
 */

/**
 * @author Merve Cerit 
 *
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Merve
 */
@WebServlet("/Merve")
public class Merve extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Merve() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		String selection = request.getParameter("Age");
		out.println("<html>");
		out.println("<body>");
		if(selection!=null){
			String year = String.valueOf(2016-Integer.valueOf(selection));
			ArrayList<Merve_Data> liste = Merve_DatabaseConnection.getActors(year);
			if(liste.size()==0){
				out.println("<p>Unfortunately, there is no actors with queried age and twitter username in the database. Go back and try a different age.</p>");
			}
			if(liste.size()!=0){
				out.println("<h2>Here your results! Actors are ordered in their birth dates. Actor name, Birth Date and Twitter Usernames are given respectively.</h2><br>");
			for(int i=0;i<liste.size();i++){
				out.println("Result"+" "+ (i+1) +":"+" "+liste.get(i).toString()+"</br>");
		}
		}
		}
		if(selection == null || selection.equals("main")){
			out.println("<p><h2><img src='https://s-media-cache-ak0.pinimg.com/736x/0f/89/b9/0f89b98b793bd2aedd16ad3484128f02.jpg' + alt='Smiley face' + width='60'+ height='60'; <h2>Welcome to Merve's query page.</h2></p>");
			out.println("<p>Please enter an age (0-51) to see actors with their Twitter usernames. The results will be sorted according to their birth dates, corresponding to their ages.<form action='Merve' method='get'><fieldset><legend>Make a query:</legend>Age:<br><input type='text' name='Age'><br><br><input type='submit' value='Submit'></fieldset></form></p>");
			out.println("<p><h3>Other Options</h3></p>");
			out.println("<p><a href=Initialization>- Initialize the database<a></p>");
			out.println("<p>(Wikidata is the main source for my database.)</p>");
			
		}
		out.println("</body>");
		out.println("</html>");
        }
    		

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
