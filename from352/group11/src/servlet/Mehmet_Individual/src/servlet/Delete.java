package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import others.DatabaseConnection;

/**
 * Servlet implementation class Delete
 * Deletes all rows from database.
 */
@WebServlet("/Delete")
public class Delete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		out.println("<html><body>");
		if(DatabaseConnection.deleteDatabase())
			out.println("Database has been deleted");
		else
			out.println("Database couldn't been deleted!");
		out.println("<br/>");
		out.println("<a href=\"Mehmet\"> Click to turn back </a> </body> </html>");	
	}
}
