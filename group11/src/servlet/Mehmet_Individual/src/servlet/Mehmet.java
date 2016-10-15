package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Mehmet
 */
@WebServlet("/Mehmet")
public class Mehmet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Main page.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		PrintWriter out = response.getWriter();
		out.print("<html><body>");
		out.println("<form action=\"Search\"> Enter a number between 2-88 to see who won the Academy Award <br/>" + 
				"<input type=\"text\" name=\"year\"> <br/>" + 
				"  <input type=\"submit\" value=\"Submit\"> <br/> </form>");
		out.println("<a href=\"Saved\"> Click to see saved entries </a> <br/>");
		out.println("<a href=\"Initialize\"> Initialize database </a> <br/> ");
		out.println("<a href=\"Delete\"> Delete database </a> <br/> ");
		
		out.println("</body></html>");
	}

}