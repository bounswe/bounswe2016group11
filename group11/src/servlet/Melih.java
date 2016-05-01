package net.codejva;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import com.hp.hpl.jena.rdf.model.Model;

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
		Integer menu = 0;
		PrintWriter out = response.getWriter();
		String my_query = request.getParameter("my_query");
		if (my_query == null){out.println("<form action='Melih' method='get'> Please enter a query:<br><input type='text' name='my_query'><br><br><input type='submit' value='Submit'></form>");}
		else{out.println("<html>");
		out.println("<body>");
		out.println("<p>" + my_query + "</p>");
		out.println("</body>");
		out.println("</html>");}
	}
	
	
}