

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This class serves as the main servlet for accessing the American Soccer Teams
 * project.
 * 
 * @author Deniz Celik
 * @version 1.0
 * @since 05102016
 */
@WebServlet("/Deniz")
public class Deniz extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public Deniz() {
        super();
    }
    
    /**
     * This method is the core of the servlet. It handles requests provided by the user
     * and navigates them to the request location via the switch variable. The method
     * heavily uses the {@link Deniz_DatabaseConnection} class for accessing the database
     * and tables. The HTML code used, is rudimentary and heavily influenced by tutorials
     * found online, as the author had no prior experience in that language. 
     * 
     * @param request The HTML request provided to the servlet
     * @param response The response provided to the users browser
     * @throws ServletException General exception thrown when the servlet encounters difficulty.
     * @throws IOException Signals that an I/O exception of some sort has occurred
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String clickTarget = request.getParameter("switch");
		out.println("<html>");
		out.println("<body>");
		if(clickTarget == null || clickTarget.equals("main")){
			out.println("<h1>Inception Year of American Soccer Teams</h1>");
			out.println("<h3>This is a class project for CMPE352 at Bogazici University,"
						+ " created by Deniz Celik, using the WikiData api for queries</h3>");
			out.println("<p>Please pick one of the options below to proceed! <small> (Refreshing the database can take a few seconds, please be patient!)</small></p>");
			out.println("<p><a href=Deniz?switch=makeQuery>Search for a team by year of inception</p>");
			out.println("<p><a href=Deniz?switch=addUserData>Add your own team data</p>");
			out.println("<p><a href=Deniz?switch=seeUserData>See saved and added team data</p>");
			out.println("<p><a href=Deniz?switch=flushWikiTable>Refresh the WikiData</p>");
			out.println("<p><a href=Deniz?switch=flushUserTable>Erase your saved data</p>");
			out.println("<p><a href=Deniz?switch=flushDB>Refresh WikiData and Erase your saved data</p>");
			
		} else if(clickTarget.equals("flushDB")){
			flushDatabase();
			out.println("<p>Erased your data successfully!</p>");
			out.println("<p>Refreshed WikiData successfully!</p>");
			out.println("<p><a href=Deniz?switch=main>Return to Main Page</p>");
			
		} else if(clickTarget.equals("flushWikiTable")){
			flushWikiTable();
			out.println("<p>Refreshed WikiData successfully!</p>");
			out.println("<p><a href=Deniz?switch=main>Return to Main Page</p>");
			
		} else if(clickTarget.equals("flushUserTable")){
			flushUserTable();
			out.println("<p>Erased your data successfully!</p>");
			out.println("<p><a href=Deniz?switch=main>Return to Main Page</p>");
			
		} else if(clickTarget.equals("queryResults")){
			String queryYearStr = request.getParameter("inputYear");
			int queryYear = Integer.valueOf(queryYearStr);
			ArrayList<Deniz_Data> queryResult = Deniz_DatabaseConnection.queryData(queryYear, true);
			ArrayList<Deniz_Data> userResult = Deniz_DatabaseConnection.queryData(queryYear, false);
			for (int i = 0; i<userResult.size();i++) {
				Deniz_Data uData = userResult.get(i);
				for (int j = 0; j<queryResult.size();j++){
					Deniz_Data qData = queryResult.get(j);
					if(uData.getTeam().equals(qData.getTeam()) && uData.getYear()==qData.getYear()){
						userResult.remove(i);
					}
				}
			}
			queryResult.addAll(userResult);
			if(queryResult.size()==0){
				out.println("<p>No Teams were established in the year "+queryYearStr+"</p>");
				out.println("<p>The teams established around that year are below: </p>");
				queryResult = semanticQuery(queryYear, true);
			}
			out.println("<p>To save a team, check the box next to the entry and click the save button below</p>");
			for(int i = 0;i<queryResult.size();i++){
				out.println("<form action='Deniz' method='get'>"
						+ "<input type = 'checkbox' name='toSave"+i+"' value='isChecked'>"
						+ " Team Name: "
						+ queryResult.get(i).getTeam()
						+ " | Year of Inception: "
						+ String.valueOf(queryResult.get(i).getYear())
						+ "<br><br>");
			}
			out.println("<input type='hidden' name='switch' value='addedUserData'/>"
						+ "<input type='hidden' name='queryYear' value='"+queryYearStr+"'/>"
						+ "<input type = 'submit' value = 'Save Teams'>"
						+ "</form>");
			out.println("<p><a href=Deniz?switch=addUserData>Not finding what you want? Add your own team and year!</p>");
			out.println("<p><a href=Deniz?switch=main>Return to Main Page</p>");
			
		} else if(clickTarget.equals("addUserData")){
			out.println("<form action='Deniz' method='get'>"
					+ "Enter the name of the team to add:"
					+ "<br><input type='hidden' name='switch' value='addResults'/>"
					+ "<input type='text' name='addName'>"
					+ "<br><br>");
			out.println("<form action='Deniz' method='get'>"
					+ "Enter the inception year of the team to add:"
					+ "<br><input type='hidden' name='switch' value='addResults'/>"
					+ "<input type='text' name='addYear'>"
					+ "<br><br>"
					+ "<input type='submit' value='Add Team'></form>");
			out.println("<p><a href=Deniz?switch=main>Return to Main Page</p>");
			
		} else if(clickTarget.equals("makeQuery")){
			out.println("<form action='Deniz' method='get'>"
					+ "Please enter a year(only integers!) to see the American soccer team(s) that were begun on or around that year:"
					+ "<br><input type='hidden' name='switch' value='queryResults'/>"
					+ "<input type='text' name='inputYear'>"
					+ "<br><br>"
					+ "<input type='submit' value='Make Query'></form>");
			out.println("<p><a href=Deniz?switch=main>Return to Main Page</p>");
			
		} else if(clickTarget.equals("addResults")){
			String newTeam = request.getParameter("addName");
			String newYearStr = request.getParameter("addYear");
			int newYear = Integer.valueOf(newYearStr);
			Deniz_DatabaseConnection.addData(new Deniz_Data(newTeam, newYear), false);
			out.println("<p>Sucessfully added the new team!</p>");
			out.println("<p><a href=Deniz?switch=seeUserData>Go to Saved Data</p>");
			out.println("<p><a href=Deniz?switch=main>Return to Main Page</p>");
			
		} else if(clickTarget.equals("seeUserData")){
			ArrayList<Deniz_Data> savedData = Deniz_DatabaseConnection.getData(false);
			if(savedData.size()==0){
				out.println("<p>No teams are currently saved</p>");
			}
			for(int i = 0;i<savedData.size();i++){
				out.println("<form action='Deniz' method='get'>"
						+ "<input type = 'checkbox' name='toDelete"+i+"' value='isChecked'>"
						+ " Team Name: "
						+ savedData.get(i).getTeam()
						+ " | Year of Inception: "
						+ String.valueOf(savedData.get(i).getYear())
						+ "<br><br>");
			}
			out.println("<input type='hidden' name='switch' value='deletedUserData'/>"
						+ "<input type = 'submit' value = 'Delete Selected Teams'>"
						+ "</form>");
			out.println("<p><a href=Deniz?switch=addUserData>Add your own team data</p>");
			out.println("<p><a href=Deniz?switch=main>Return to Main Page</p>");
			
		} else if(clickTarget.equals("addedUserData")){
			String oldQueryYearStr = request.getParameter("queryYear");
			int oldQueryYear = Integer.valueOf(oldQueryYearStr);
			ArrayList<Deniz_Data> queryResult = Deniz_DatabaseConnection.queryData(oldQueryYear, true);
			if(queryResult.size()==0){
				queryResult=semanticQuery(oldQueryYear, true);
			}
			for(int i=0;i<queryResult.size();i++){
				String tempSaveVal = request.getParameter("toSave"+i);
				if(tempSaveVal!=null){
					if(tempSaveVal.equals("isChecked")){
						Deniz_DatabaseConnection.addData(queryResult.get(i), false);
					}
				}
			}
			out.println("<p>Data Succesfully Added!</p>");
			out.println("<p><a href=Deniz?switch=seeUserData>Go to Saved Data</p>");
			out.println("<p><a href=Deniz?switch=queryResults&inputYear="+oldQueryYearStr+">Return to Query Result</p>");
			out.println("<p><a href=Deniz?switch=main>Return to Main Page</p>");
			
		} else if(clickTarget.equals("deletedUserData")){
			ArrayList<Deniz_Data> savedData = Deniz_DatabaseConnection.getData(false);
			for(int i=0;i<savedData.size();i++){
				String tempDeleteVal = request.getParameter("toDelete"+i);
				if(tempDeleteVal!=null){
					if(tempDeleteVal.equals("isChecked")){
						Deniz_DatabaseConnection.removeData(savedData.get(i));
					}
				}
			}
			out.println("<p>Data Succesfully Removed!</p>");
			out.println("<p><a href=Deniz?switch=seeUserData>Return to Saved Data</p>");
			out.println("<p><a href=Deniz?switch=main>Return to Main Page</p>");
		
		}
		out.println("</body>");
		out.println("</html>");
	}
	
	/**
	 * This method is called for finding of data near the year requested. Used
	 * when a queried year is not found in the database.
	 * 
	 * @param year Year to be queried around
	 * @param isWiki which table should be semanticQueried
	 * @return ArrayList of Deniz_Data containing the data closest to the queried year
	 */
	private ArrayList<Deniz_Data> semanticQuery(int year, Boolean isWiki){
		ArrayList<Deniz_Data> results = new ArrayList<Deniz_Data>();
		ArrayList<Deniz_Data> fullData = Deniz_DatabaseConnection.getData(true);
		Collections.sort(fullData);
		int insertionPoint = (Collections.binarySearch(fullData, new Deniz_Data("name",year))+1)*-1;
		int upperbound = insertionPoint;
		if(upperbound>=fullData.size()){
			upperbound=fullData.size()-1;
		}
		int lowerbound = upperbound-1;
		if(lowerbound<0){
			lowerbound=0;
		}
		for (int i = 0; i<15;i++){
			if(upperbound>fullData.size()-1){
				results.add(fullData.get(lowerbound));
				lowerbound--;
			} else if(lowerbound<0){
				results.add(fullData.get(upperbound));
				upperbound++;
			} else if( (Math.abs(year-fullData.get(upperbound).getYear())) > (Math.abs(year-fullData.get(lowerbound).getYear())) ){
				results.add(fullData.get(lowerbound));
				lowerbound--;
			} else {
				results.add(fullData.get(upperbound));
				upperbound++;
				
			}
		}
		Collections.sort(results);
		if( (Math.abs(year-results.get(0).getYear())) > (Math.abs(year-results.get(results.size()-1).getYear()))){
			Collections.reverse(results);
		}
		return results;
	}
	
	/**
	 * Helper method to flush the UserTable
	 */
	private void flushUserTable(){
		Deniz_DatabaseConnection.dropTable(false);
		Deniz_DatabaseConnection.createTable(false);
	}
	
	/**
	 * Helper method to flush the WikiTable
	 */
	private void flushWikiTable(){
		Deniz_DatabaseConnection.dropTable(true);
		Deniz_DatabaseConnection.createTable(true);
		JSONArray dataArray = new JSONArray();
		try{
			dataArray = Deniz_WikidataToJson.queryTeams();
		} catch (Exception e){
			e.printStackTrace();
		}
		for(int i = 0; i<dataArray.length(); i++){
			JSONObject tempItem = dataArray.getJSONObject(i);
			String tempTeam = tempItem.getJSONObject("teamLabel").getString("value");
			String tempYearStr = tempItem.getJSONObject("yearLabel").getString("value");
			tempYearStr = tempYearStr.substring(0, Math.min(tempYearStr.length(), 4));
			int tempYear = Integer.valueOf(tempYearStr);
			Deniz_Data tempData = new Deniz_Data(tempTeam,tempYear);
			//System.out.println("Team: "+ tempTeam +" Year: "+tempYearStr);
			Deniz_DatabaseConnection.addData(tempData,true);
		}
	}
	/**
	 * Helper method to flush the database.
	 */
	private void flushDatabase(){
		Deniz_DatabaseConnection.dropDatabase();
		Deniz_DatabaseConnection.createDatabase();
		Deniz_DatabaseConnection.useDatabase();
		flushUserTable();
		flushWikiTable();
	}
}
