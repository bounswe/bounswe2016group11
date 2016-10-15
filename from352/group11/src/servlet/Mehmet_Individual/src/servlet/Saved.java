package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import others.DatabaseConnection;
import others.Item;


/**
 * 
 * Servlet implementation class Saved
 * 
 * Shows the rows that is checked by user at initialization.
 */
@WebServlet("/Saved")
public class Saved extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		out.println("<html><body>");
		ArrayList<Item> checkedList = DatabaseConnection.getCheckedList();
		for(int i=0;i<checkedList.size();i++){
			out.println(checkedList.get(i).year + " -> " + checkedList.get(i).name +  "<br/>");
		}
		out.println("<br/><br/><a href=\"Mehmet\"> Click to turn back </a> </body> </html>");
		
	}

}
