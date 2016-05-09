package net.codejva;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
    
    String printTable(ArrayList<Melih_Data> allData){
    	String myTableout = "";
    	myTableout+="<table>";
    	myTableout+="<tr>";
    		myTableout+="<th><strong>Year of Ascension</strong></th>";
    		myTableout+="<th><strong>Name of the Emperor</strong></th>";
    	myTableout+="</tr>";
		for(int i = 0; i < allData.size();i++){
			if(allData.get(i).isSelected){
				myTableout+="<tr>";
				myTableout+="<td align='center'>"+ allData.get(i).date +"</td>";
				myTableout+="<td align='center'>"+ allData.get(i).emperor +"</td>";
				myTableout+="</tr>";
			}
		}
		return myTableout;
    }
    
    void flushDatabase(){
    	Melih_DatabaseConnection.dropDatabase();
		Melih_DatabaseConnection.createDatabase();
		Melih_DatabaseConnection.useDatabase();
		Melih_DatabaseConnection.createTable();
		JSONArray myJSONarray = new JSONArray();
		String query = "SELECT%20?emperorLabel%20?dateLabel%20WHERE%20{%20?emperor%20wdt:P31%20wd:Q5%20.%20?emperor%20p:P39%20?position_held_statement%20.%20?position_held_statement%20ps:P39%20wd:Q842606.%20?position_held_statement%20pq:P580%20?date%20.%20SERVICE%20wikibase:label%20{%20bd:serviceParam%20wikibase:language%20%27en%27%20.%20}%20}%20ORDER%20BY%20?date&format=json";
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
    }

    ArrayList<Melih_Data> getQueryResults(int intQuery){
    	ArrayList<Melih_Data> allData =  Melih_DatabaseConnection.getData();
		
		Collections.sort(allData, new Comparator<Melih_Data>() {
		@Override
			public int compare(Melih_Data o1, Melih_Data o2) {
				return o1.date.compareTo(o2.date);
			}
    	});
		
		Integer minIndex = 0;
		Integer minValue = Math.abs(allData.get(0).date - intQuery);
		for(int i = 1; i<allData.size(); i++){
			if (Math.abs(allData.get(i).date - intQuery)<minValue) {
					minIndex = i;
					minValue =Math.abs(allData.get(i).date - intQuery); 	
				}
		}
		ArrayList<Melih_Data> editData = allData;
		ArrayList<Melih_Data> resultData = new ArrayList<Melih_Data>();
		resultData.add(editData.get(minIndex));
		
		for(int i = 0; i< 9; i++){
			if(minIndex == (editData.size()-1)){
				resultData.add(editData.get(minIndex-1));
				editData.remove(minIndex-1);
				minIndex--;
			}else if(minIndex == 0){
				resultData.add(editData.get(minIndex+1));
				editData.remove(minIndex+1);
			}else if(Math.abs(editData.get(minIndex-1).date - intQuery)<Math.abs(editData.get(minIndex+1).date - intQuery)){
				resultData.add(editData.get(minIndex-1));
				editData.remove(minIndex-1);
				minIndex--;
			}else {
				resultData.add(editData.get(minIndex+1));
				editData.remove(minIndex+1);
			}
		}
		return resultData;
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String selection = request.getParameter("menu");
		out.println("<html>");
		out.println("<body>");
		if(selection == null || selection.equals("main")){
			out.println("<h3>Roman Emperors and their Years of Ascension</h3>");
			out.println("<p>Welcome. This is a page created by Melih Barsbey. Please choose one of the options below.</p>");
			out.println("<p><a href=Melih?menu=makequery>Make a query (enter a year to see which emperors ascended to Roman Empire's throne that year, or years close by)<a></p>");
			out.println("<p><a href=Melih?menu=flush>Initialize (or reset) the database from wikidata.org<a></p>");
			out.println("<p><a href=Melih?menu=seeSaved>See your saved entries<a></p>");
		} else if (selection.equals("makequery")){
			out.println("<form action='Melih' method='get'>Please enter a year to see the Roman emperor or emperors that have ascended to the throne that year (or years close by):<br><input type='hidden' name='menu' value='query'/><input type='text' name='input'><br><br><input type='submit' value='Submit'></form>");
			out.println("<p><a href=Melih?menu=main>Go to main menu<a></p>");
		} else if (selection.equals("query")){
			String queriedYear = request.getParameter("input");
			Integer intQuery = Integer.parseInt(queriedYear);
			ArrayList<Melih_Data> myData = Melih_DatabaseConnection.makeQuery(intQuery);
			if (myData.size()>0){
				out.println("There exist(s) emperor(s) who ascended to the throne at that year. See below for these emperor or emperors. Listed further below are those that ascended in years that are closest.");
			}else{
				out.println("There were no emperors that ascended to the throne at that year! See below for emperors that ascended closest to that year.");	
			}
			out.println("<br>You can save any of the emperors in the database by checking on the checkboxes and clicking the 'Save' button.");
			
			ArrayList<Melih_Data> resultData = getQueryResults(intQuery);
			
			out.println("<form action='Melih' method='get'>");
			out.println("<table>");
			
			out.println("<tr>");
				out.println("<th><strong>Save</strong></th>");
				out.println("<th><strong>Year of Ascension</strong></th>");
				out.println("<th><strong>Name of the Emperor</strong></th>");
			out.println("</tr>");
			for(int i = 0; i < resultData.size();i++){
				out.println("<tr>");
				out.println("<td><input type='checkbox' name='checkbox_" + i + "' value='checked' /></td>");
				out.println("<td align='center'>"+ resultData.get(i).date +"</td>");
				out.println("<td align='center'>"+ resultData.get(i).emperor +"</td>");
				out.println("</tr>");
			}
		out.println("</table>");
		out.println("<input type='hidden' name='input' value='"+queriedYear+"'/><input type='hidden' name='menu' value='save'/><input type='submit' value='Save'></form>");
		out.println("Would you like to add an(other) emperor that ascended to the throne at year "+intQuery+"?<br>If yes, please enter the name below and click 'Add Emperor' button. Remember: The name of your emperor cannot be the same with that of an already existing one!");
		out.println("<form action='Melih' method='get'><input type='hidden' name='queriedYear' value='"+queriedYear+"'/><input type='hidden' name='menu' value='addNewEmperor'/><input type='text' name='input'><br><br><input type='submit' value='Add Emperor'></form>");
		out.println("<p><a href=Melih?menu=main>Go to main menu<a></p>");
		} else if (selection.equals("flush")){
			flushDatabase();
			out.println("<p>Database is renewed!</p>");
			out.println("<p><a href=Melih?menu=main>Go to main menu<a></p>");
		} else if(selection.equals("save")){
			String queriedYear = request.getParameter("input");
			Integer intQuery = Integer.parseInt(queriedYear);
			
			ArrayList<Melih_Data> resultData = getQueryResults(intQuery);
			

			for (int i = 0; i<resultData.size();i++){
				if(request.getParameter("checkbox_"+i)!=null){
					String whetherChecked = request.getParameter("checkbox_"+i);
					if (whetherChecked.equals("checked")) Melih_DatabaseConnection.saveData(resultData.get(i));
				}
			}
			out.println("Your requests have been saved!");
			out.println("<p><a href=Melih?menu=main>Go to main menu<a></p>");
		}else if(selection.equals("seeSaved")){
			out.println("Here are your saved entries:");
			ArrayList<Melih_Data> allData = Melih_DatabaseConnection.getData();
			out.println(printTable(allData));

			out.println("<p><a href=Melih?menu=main>Go to main menu<a></p>");
		out.println("</table>");
		}
		else if(selection.equals("addNewEmperor")){
			String emperorName = request.getParameter("input");
			String queriedYear = request.getParameter("queriedYear");
			Integer intYear =Integer.parseInt(queriedYear);
			Melih_DatabaseConnection.addData(new Melih_Data(emperorName, intYear));
			Melih_DatabaseConnection.orderTable();
			out.println("<p>Your request has been added!</p>");
			out.println("<p><a href=Melih?menu=main>Go to main menu<a></p>");
		}
		out.println("</body>");
		out.println("</html>");
	}	
}

//JSONObject tempRecord = myJSONarray.getJSONObject(10);
//String dene = tempRecord.getJSONObject("emperorLabel").getString("value");
//System.out.println(dene);

/*Melih_DatabaseConnection.dropDatabase();
Melih_DatabaseConnection.createDatabase();
Melih_DatabaseConnection.useDatabase();
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
}*/

/*out.println("<table>");
out.println("<tr>");
	out.println("<th><strong>Year of Ascension</strong></th>");
	out.println("<th><strong>Name of the Emperor</strong></th>");
out.println("</tr>");
for(int i = 0; i < allData.size();i++){
	if(allData.get(i).isSelected){
		out.println("<tr>");
		out.println("<td align='center'>"+ allData.get(i).date +"</td>");
		out.println("<td align='center'>"+ allData.get(i).emperor +"</td>");
		out.println("</tr>");
	}
}*/

/*ArrayList<Melih_Data> allData =  Melih_DatabaseConnection.getData();

Collections.sort(allData, new Comparator<Melih_Data>() {
@Override
	public int compare(Melih_Data o1, Melih_Data o2) {
		return o1.date.compareTo(o2.date);
	}
});

Integer minIndex = 0;
Integer minValue = Math.abs(allData.get(0).date - intQuery);
for(int i = 1; i<allData.size(); i++){
	if (Math.abs(allData.get(i).date - intQuery)<minValue) {
			minIndex = i;
			minValue =Math.abs(allData.get(i).date - intQuery); 	
		}
}
ArrayList<Melih_Data> editData = allData;
ArrayList<Melih_Data> resultData = new ArrayList<Melih_Data>();
resultData.add(editData.get(minIndex));

for(int i = 0; i< 9; i++){
	if(minIndex == (editData.size()-1)){
		resultData.add(editData.get(minIndex-1));
		editData.remove(minIndex-1);
		minIndex--;
	}else if(minIndex == 0){
		resultData.add(editData.get(minIndex+1));
		editData.remove(minIndex+1);
	}else if(Math.abs(editData.get(minIndex-1).date - intQuery)<Math.abs(editData.get(minIndex+1).date - intQuery)){
		resultData.add(editData.get(minIndex-1));
		editData.remove(minIndex-1);
		minIndex--;
	}else {
		resultData.add(editData.get(minIndex+1));
		editData.remove(minIndex+1);
	}
}*/

/*ArrayList<Melih_Data> allData =  Melih_DatabaseConnection.getData();
Integer minIndex = 0;
Integer minValue = Math.abs(allData.get(0).date - intQuery);
for(int i = 1; i<allData.size(); i++){
	if (Math.abs(allData.get(i).date - intQuery)<minValue) {
			minIndex = i;
			minValue =Math.abs(allData.get(i).date - intQuery); 	
		}
}
ArrayList<Melih_Data> editData = allData;
ArrayList<Melih_Data> resultData = new ArrayList<Melih_Data>();
resultData.add(editData.get(minIndex));
			
for(int i = 0; i< 9; i++){
	if(Math.abs(editData.get(minIndex-1).date - intQuery)<Math.abs(editData.get(minIndex+1).date - intQuery)){
		resultData.add(editData.get(minIndex-1));
		editData.remove(minIndex-1);
		minIndex--;
	} else {
		resultData.add(editData.get(minIndex+1));
		editData.remove(minIndex+1);
	}
}*/

