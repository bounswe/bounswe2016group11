
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Mustafa_Input
 * User makes query and saving in this servlet.
 */
@WebServlet("/Mustafa_Input")
public class Mustafa_Input extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Mustafa_Input() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String select = request.getParameter("menu");		
		if(select == null){		
			out.println("<p><a href=Mustafa>Go back!<a></p>");
			out.println("<form action='Mustafa_Input' method='get'><p>Enter a population</p>"+
					"<input type='hidden' name='menu' value='query'/>"+
					"<input type='text' name='input'><br><input type='submit' value='Submit'></form>");	
		} else if (select.equals("query")){
			Integer myQuery = Integer.parseInt(request.getParameter("input"));
			ArrayList<Mustafa_Data> queryData = Mustafa_DatabaseConnection.makeQuery(myQuery);
			if (queryData.size()>0){
				out.println("<p>There exists countries whose population is exactly your query.</p> ");
			}else{
				out.println("<p>There is no country whose population is exactly your query.</p>");	
			}
			Integer minIdx = 0;
			Integer minVal = Integer.MAX_VALUE;
			ArrayList<Mustafa_Data> myData =  Mustafa_DatabaseConnection.getData();
			for(int i = 0; i<myData.size(); i++){
				Integer absDiff = Math.abs(myQuery - myData.get(i).population);
				if (absDiff<minVal) {
						minIdx = i;
						minVal = absDiff;
				}
			}
			ArrayList<Mustafa_Data> results = new ArrayList<Mustafa_Data>();
			results.add(myData.get(minIdx));			
			for(int i = 0; i< 20; i++){
				
				if(minIdx == (myData.size()-1)){
					results.add(myData.get(minIdx-1));
					myData.remove(minIdx-1);
					minIdx--;
				}else if(minIdx == 0){
					results.add(myData.get(minIdx+1));
					myData.remove(minIdx+1);
				} else if(Math.abs(myQuery - myData.get(minIdx-1).population)<Math.abs(myQuery - myData.get(minIdx+1).population)){
					results.add(myData.get(minIdx-1));
					myData.remove(minIdx-1);
					minIdx--;
				} else {
					results.add(myData.get(minIdx+1));
					myData.remove(minIdx+1);
				}
			}
			out.println("<form action='Mustafa_Input' method='get'>");
			out.println("<table>");			
			out.println("<tr>");
			out.println("<th><strong>Check</strong></th>");
			out.println("<th><strong>Population</strong></th>");
			out.println("<th align='left'><strong>Country</strong></th>");
			out.println("</tr>");
			for(int i = 0; i < results.size();i++){			
				out.println("<tr>");
				out.println("<td><input type='checkbox' name='checkbox_" + i + "' value='checked' /></td>");
				out.println("<td align='right'>"+ results.get(i).population +"</td>");
				out.println("<td align='left'>"+ results.get(i).country +"</td>");
				out.println("</tr>");
			}			
		out.println("</table>");
		out.println("<input type='hidden' name='input' value='"+myQuery+"'/>");
		out.println("<input type='hidden' name='menu' value='save'/><input type='submit' value='Save'></form>");
		out.println("<br>Check the checkboxes and click Save button to save some of these countries.");
		out.println("<p><a href=Mustafa>Go back!<a></p>");	
		} else if(select.equals("save")){
			Integer myQuery = Integer.parseInt(request.getParameter("input"));
			ArrayList<Mustafa_Data> myData =  Mustafa_DatabaseConnection.getData();
			Integer minIdx = 0;
			Integer minVal = Integer.MAX_VALUE;
			for(int i = 0; i<myData.size(); i++){
				Integer absDiff = Math.abs(myQuery - myData.get(i).population);
				if (absDiff<minVal) {
						minIdx = i;
						minVal = absDiff; 	
					}
			}		
			ArrayList<Mustafa_Data> results = new ArrayList<Mustafa_Data>();
			results.add(myData.get(minIdx));						
			for(int i = 0; i< 20; i++){
				
				if(Math.abs(myQuery - myData.get(minIdx-1).population)<Math.abs(myQuery - myData.get(minIdx+1).population)){
					results.add(myData.get(minIdx-1));
					myData.remove(minIdx-1);
					minIdx--;
				} else {
					results.add(myData.get(minIdx+1));
					myData.remove(minIdx+1);
				}
			}	
			for (int i = 0; i<results.size();i++){
				if(request.getParameter("checkbox_"+i)!=null){
					String isChecked = request.getParameter("checkbox_"+i);
					if (isChecked.equals("checked")) 
						Mustafa_DatabaseConnection.saveData(results.get(i));
				}
			}
			out.println("<p>Saved</p>");
			out.println("<p><a href=Mustafa>Go back!<a></p>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
