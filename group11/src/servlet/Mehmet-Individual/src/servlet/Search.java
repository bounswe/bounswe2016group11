package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import others.DatabaseConnection;
import others.Item;

/**
 * Shows the result of searches.
 * Servlet implementation class Search
 */
@WebServlet("/Search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * Shows the result or says that it couldn't find
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		String year = request.getParameter("year");
		Item result = DatabaseConnection.getItem(year);
		out.println("<html><body>");
		if(result != null)
			out.println(result.toString());
		else
			out.println("Ooops, we have no data for this year");
		out.println("<br/><br/><a href=\"Mehmet\"> Click to turn back </a> </body> </html>");

	}


}
