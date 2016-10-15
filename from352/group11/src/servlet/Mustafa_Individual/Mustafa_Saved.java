
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Mustafa_Saved
 * Shows entries saved by user.
 */
@WebServlet("/Mustafa_Saved")
public class Mustafa_Saved extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Mustafa_Saved() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		out.println("<p><a href=Mustafa>Go back!<a></p>");
		out.println("Saved entries:</br>");
		out.println("<table>");
		out.println("<tr>");
		out.println("<th><strong>Population</strong></th>");
		out.println("<th align='left'><strong>Country</strong></th>");
		out.println("</tr>");
		ArrayList<Mustafa_Data> myData = Mustafa_DatabaseConnection.getData();
		for(int i = 0; i < myData.size();i++){
			if(myData.get(i).isSelected){
				out.println("<tr>");
				out.println("<td align='right'>"+ myData.get(i).population +"</td>");
				out.println("<td align='left'>"+ myData.get(i).country +"</td>");
				out.println("</tr>");
			}
		}
		out.println("</table>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
