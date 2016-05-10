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
* This servlet is the main class for the individual implementation project Roman 
* Emperors and their Years of Ascension.
*
* @author  Melih Barsbey
* @version 1.0
* @since   2016-05-10 
*/  

@WebServlet("/Melih")

public class Melih extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    public Melih() {
        super();
    }
    
    /**
	   * This method takes an array of data from the servlet doGet method 
	   * which contains the entities the user wanted to save. This method creates 
	   * and returns the html code required to print these entities out. 
	   * 
	   * @param allData The array includes the saved records from the doGet method.
	   * @return myTableOut This string includes the html code to be printed.
	   */
    public String printTable(ArrayList<Melih_Data> allData){
    	//The method receives all database data as its input parameter.
    	String myTableOut = "";		//The method will return this string including html code, which the doGet method will print out 
    	myTableOut+="<table>";
    	myTableOut+="<tr>";
			myTableOut+="<th colspan='2'><strong>Here are your saved entries:</strong></th>";
		myTableOut+="</tr>";
    	myTableOut+="<tr>";
    		myTableOut+="<th><strong>Year of Ascension</strong></th>";	//Table headings are being printed
    		myTableOut+="<th><strong>Name of the Emperor</strong></th>";
    	myTableOut+="</tr>";
		for(int i = 0; i < allData.size();i++){ //Data headings have been printed out, now the ascension dates and emperor names are being printed.
			if(allData.get(i).isSelected){
				myTableOut+="<tr>";			
				myTableOut+="<td align='center'>"+ allData.get(i).date +"</td>";
				myTableOut+="<td align='center'>"+ allData.get(i).emperor +"</td>"; 
				myTableOut+="</tr>";
			}
		}
		return myTableOut;	//This string will later be printed by the doGet method
    }
    
    /**
	   * This method takes the array of saved data from the servlet's doGet
	   * method. This method create and returns the html code required to print these entities
	   * out in a string. 
	   * 
	   * @param allSaves The array includes the save history records from the doGet method.
	   * @return myTableOut This string includes the html code to be printed.
	   */
    
public String printSaveHistory(ArrayList<String> allSaves){
    	String myTableOut = "";		//The method will return this string including html code, which the doGet method will print out
    	myTableOut+="<br>";		
    	myTableOut+="<table>";
    	myTableOut+="<tr>";
			myTableOut+="<th colspan='2'><strong>Here is your save history:</strong></th>";
		myTableOut+="</tr>";
    	myTableOut+="<tr>";
    		myTableOut+="<th><strong>Save Number</strong></th>";
    		myTableOut+="<th><strong>Emperors Saved</strong></th>";
    	myTableOut+="</tr>";
		for(int i = 0; i < allSaves.size();i++){	//All the saves are being printed out.
				int number = i+1;
				myTableOut+="<tr>";
				myTableOut+="<td align='left'>"+ number +"</td>";
				myTableOut+="<td align='left'>"+ allSaves.get(i) +"</td>";
				myTableOut+="</tr>";
		}
		return myTableOut;		//This string will later be printed by the doGet method
    }
    
   /**
   * This method takes two arrays of data from inside the servlet {@link #doGet [doGet]} method, 
   * one which contains the entities in the database, and the other is the  
   * result user's query. The method returns the query table to be printed out. 
   * 
   * @param myData The array includes all the entities in the database table.
   * @param resultData The array includes the ten entites chosen to be as a result of the query.
   * @param queriedYear This is the string, containing the year the user entered .
   * @return outputString This string includes the html code to be printed.
   */
 	
    public String printOutputTable(ArrayList<Melih_Data> myData, ArrayList<Melih_Data> resultData, String queriedYear){

    	Integer intQuery = Integer.parseInt(queriedYear);	
    	String outputString = ""; 	//The method will return this string including html code, which the doGet method will print out
		if (myData.size()>0){		//This if clause determines whether there are any matches to the query of the user. Message changes according to that.
			outputString+="There exist(s) emperor(s) who ascended to the throne at that year. See below for these emperor or emperors. Listed further below are those that ascended in years that are closest.";
		}else{
			outputString+="There were no emperors that ascended to the throne at that year! See below for emperors that ascended closest to that year. You can save any of the emperors in the database for later review by checking on the checkboxes and clicking the 'Save' button.";	
		}
		outputString+="<br>The emperors are <strong>ordered</strong> according to how close they became the emperor compared to the date you entered.";
		outputString+="<form action='Melih' method='get'>";
		outputString+="<table>";
		outputString+="<tr>";
			outputString+="<th><strong>Save</strong></th>";
			outputString+="<th><strong>Year of Ascension</strong></th>";
			outputString+="<th><strong>Name of the Emperor</strong></th>";
			outputString+="</tr>";
		for(int i = 0; i < resultData.size();i++){
			outputString+="<tr>";	//Checkboxes alongisde names and dates are being printed, so that the user can save his/her choices.
			outputString+="<td><input type='checkbox' name='checkbox_" + i + "' value='checked' /></td>";	
			outputString+="<td align='center'>"+ resultData.get(i).date +"</td>";
			outputString+="<td align='center'>"+ resultData.get(i).emperor +"</td>";
			outputString+="</tr>";
		}
	outputString+="</table>";		//This part is for users who want to add an emperor to the database.
	outputString+="<input type='hidden' name='input' value='"+queriedYear+"'/><input type='hidden' name='menu' value='save'/><input type='submit' value='Save'></form>";
	outputString+="<p style='color:red'>Do you want to add a new emperor?</p>";
	outputString+="Would you like to add an(other) emperor that ascended to the throne at year "+intQuery+"?<br>If yes, please enter the name below and click 'Add Emperor' button. Remember: The name of your emperor cannot be the same with that of an already existing one!";
	outputString+="<form action='Melih' method='get'><input type='hidden' name='queriedYear' value='"+queriedYear+"'/><input type='hidden' name='menu' value='addNewEmperor'/><input type='text' name='input'><br><br><input type='submit' value='Add Emperor'></form>";
	return outputString;
    }

    /**
	   * This method takes no parameters and returns no values. The primary funciton of this method is to
	   * to delete the database, and recreate it and the necessary table within it.
	   * Then, the predetermined query is sent to Wikidata.org using the 
	   * of {@link Melih_Wikidata#getHtml(String query) [getHtml]} method of {@link Melih_Wikidata [Melih_Wikidata]} class,
	   * demanding the relevant data in JSON format. After the
	   * data is received, it is parsed into a MelihData object and sent to the 
	   * Database to be inserted using the {@link Melih_DatabaseConnection#addData(Melih_Data myData) [addData]} method of {@link Melih_DatabaseConnection [Melih_DatabaseConnection]} 
	   * class. 
	   */
    
    public void flushDatabase(){
    	
    	Melih_DatabaseConnection.dropDatabase();	//Database is dropped
		Melih_DatabaseConnection.createDatabase();	//Database is created again
		Melih_DatabaseConnection.useDatabase();		//The database melih_database is selected
		Melih_DatabaseConnection.createDataTable();	//Table that holds the data obtained from wikidata created
		Melih_DatabaseConnection.createSaveTable();	//Table that holds save history is created
		JSONArray myJSONarray = new JSONArray();	//The JSON array will temporarily hold the information sent by Wikidata in response to query
		String query = "https://query.wikidata.org/sparql?query=SELECT%20?emperorLabel%20?dateLabel%20WHERE%20{%20?emperor%20wdt:P31%20wd:Q5%20.%20?emperor%20p:P39%20?position_held_statement%20.%20?position_held_statement%20ps:P39%20wd:Q842606.%20?position_held_statement%20pq:P580%20?date%20.%20SERVICE%20wikibase:label%20{%20bd:serviceParam%20wikibase:language%20%27en%27%20.%20}%20}%20ORDER%20BY%20?date&format=json";
		try {
			myJSONarray = Melih_Wikidata.getHtml(query);	//The data is asked through the getHtml method
		} catch (Exception e) {
			e.printStackTrace();
		}
		Melih_DatabaseConnection.useDatabase();
		for(int i = 0; i<myJSONarray.length();i++){			//The data is being parsed and recorded into the database tuple by tuple
			JSONObject tempRecord = myJSONarray.getJSONObject(i);
			String tempEmperor = tempRecord.getJSONObject("emperorLabel").getString("value");
			String tempDate = tempRecord.getJSONObject("dateLabel").getString("value");
			Integer intDate = Integer.parseInt(tempDate.substring(0,4));
			Melih_DatabaseConnection.addData(new Melih_Data(tempEmperor,intDate));
		}
    }

    /**
	   * This method takes an integer parameter (year the user entered) and
	   * finds the emperors that ascended to the throne the closest to that year.
	   * It first obtains the data from the database using the {@link Melih_DatabaseConnection#getData() [getData]}
	   * method of {@link Melih_DatabaseConnection [Melih_DatabaseConnection]}.
	   * Then it finds the 10 emperors that ascended closest to that date.
	   * Then the method returns this list.  
	   * 
	   * @param intQuery The year that the user entered
	   * @return resultData The ten emperors with ascension dates closest to the entered year.
	   */
    public ArrayList<Melih_Data> getQueryResults(int intQuery){	
    	Melih_DatabaseConnection.useDatabase();
    	ArrayList<Melih_Data> allData =  Melih_DatabaseConnection.getData();
		
		Collections.sort(allData, new Comparator<Melih_Data>() {
		@Override	//The data is first sorted according to order
			public int compare(Melih_Data o1, Melih_Data o2) {
				return o1.date.compareTo(o2.date);
			}
    	});
		
		Integer minIndex = 0;	//minIndex will hold the index of the emperor whose date of ascension is closest to that of the user's query
		Integer minValue = Math.abs(allData.get(0).date - intQuery);
		for(int i = 1; i<allData.size(); i++){	//minIndex is determined through the for loop below.
			if (Math.abs(allData.get(i).date - intQuery)<minValue) {
					minIndex = i;
					minValue =Math.abs(allData.get(i).date - intQuery); 	
				}
		}
		ArrayList<Melih_Data> editData = allData;
		ArrayList<Melih_Data> resultData = new ArrayList<Melih_Data>();
		resultData.add(editData.get(minIndex));
		
		for(int i = 0; i< 9; i++){		//This for loop determines the 10 closest records to the user's query. 10 emperors with dates of ascension that is closest. 
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
		return resultData;		//The resulting data is stored in this array which is returned to the caller method.
    }
    /**
	   * The doGet method is the main method of this servlet: this is where the requests
	   * to the application is handled. The navigation inside the application is handled through
	   * the parameters of the get request. Depending on the parameter supplied, the server
	   * directs the user to different screens and conducts the operations demanded by the user
	   * using the methdos of this class and other classes.
	   * 
	   * @param request The html request is obtained as a parameter.
	   * @param response The response to the request is also a parameter.
	   */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String selection = request.getParameter("menu");
		out.println("<html>");
		out.println("<body>");
		if(selection == null || selection.equals("main")){	//The main menu. If there are no parameters, or menu parameter is equal to 'menu', the user is directed to the main menu.
			out.println("<h3 style='color:blue; text-align:center'>Roman Emperors and their Years of Ascension</h3>");
			out.println("<p style='text-align:center'>Welcome. This is a page created by Melih Barsbey. Please choose one of the options below. This application allows you to enter a year to see which emperors ascended to Roman Empire's throne that year, or years close by. After you made a query, you can save some emperors for later review, or add a new emperor to the database.</p>");
			out.println("<p style='color:red; text-align:center'><a href=Melih?menu=makequery>Make a query<a></p>");
			out.println("<p style='color:red; text-align:center'><a href=Melih?menu=seeSaved>See your saved entries and save history<a></p>");
			out.println("<p style='color:red; text-align:center'><a href=Melih?menu=unsave>Unsave your saved entries and delete your save history<a></p>");
			out.println("<p style='color:red; text-align:center'><a href=Melih?menu=flush>Reset the database from wikidata.org<a></p>");
		} else if (selection.equals("makequery")){			//The query interface. If the user chose to make a query she/he is directed to here.
			out.println("<p>Please enter a year to see the Roman emperor or emperors that have ascended to the throne that year (or years close by).<br></p>");
			out.println("<form action='Melih' method='get' style='color:red'>Enter a year:<br><input type='hidden' name='menu' value='query'/><input type='text' name='input'><br><br><input type='submit' value='Submit'></form>");
			out.println("<p><a href=Melih?menu=main>Go to main menu<a></p>");
		} else if (selection.equals("query")){		//When the user submits their query, they are directed here, where the query results are displayed.
			String queriedYear = request.getParameter("input");
			if(queriedYear.equals("")){
				out.println("Please enter a valid query!");
				out.println("<p><a href=Melih?menu=main>Go to main menu<a></p>");
			}else{
				Integer intQuery = Integer.parseInt(queriedYear);
				Melih_DatabaseConnection.useDatabase();
				ArrayList<Melih_Data> myData = Melih_DatabaseConnection.makeQuery(intQuery);
				ArrayList<Melih_Data> resultData = getQueryResults(intQuery); 		//The query results are prepared by the getQueryResults method described above.
				String print = printOutputTable(myData, resultData, queriedYear);	//The table printout is prepared by the printOutputTable method described above. 		
				out.println(print);
				out.println("<p><a href=Melih?menu=main>Go to main menu<a></p>");
			}
		} else if (selection.equals("flush")){
			flushDatabase();	//If the user chooses to do so, the database is renewed through the flushDatabase method described above. 
			out.println("<p>Database is renewed!</p>");
			out.println("<p><a href=Melih?menu=main>Go to main menu<a></p>");
		} else if(selection.equals("save")){	//If the user chooses to save some of the query result for later review, the results are saved through the code below. This is also recorded into the save history.
			String queriedYear = request.getParameter("input");
			Integer intQuery = Integer.parseInt(queriedYear);
			ArrayList<Melih_Data> resultData = getQueryResults(intQuery);
			String saveTableData = "";
			for (int i = 0; i<resultData.size();i++){
				if(request.getParameter("checkbox_"+i)!=null){
					String whetherChecked = request.getParameter("checkbox_"+i);
					if (whetherChecked.equals("checked")){
						saveTableData += resultData.get(i).emperor + "; ";		//This is for save history
						Melih_DatabaseConnection.saveData(resultData.get(i));	//This is for marking the emperors in the database as saved.
					}
				}
			}
			Melih_DatabaseConnection.addSave(saveTableData);
			out.println("Your requests have been saved!");
			out.println("<p><a href=Melih?menu=main>Go to main menu<a></p>");
		}else if(selection.equals("seeSaved")){		//If the user wants to see the emperors she/he saved, she/he is presented with both the results she/he saved and her/his save history.
			Melih_DatabaseConnection.useDatabase();
			ArrayList<Melih_Data> allData = Melih_DatabaseConnection.getData();
			ArrayList<String> allSaves = Melih_DatabaseConnection.getSave();
			boolean isPresent = false;
			for(int i = 0; i < allData.size();i++){
				if(allData.get(i).isSelected){
					if(allData.size()>0)out.println(printTable(allData));
					isPresent = true;
					break;
				}	
			}
			if(isPresent==true)out.println(printSaveHistory(allSaves));		
			if(isPresent==false)out.println("You have no saved entries! Please make a query and save same entries.");
			out.println("<p><a href=Melih?menu=main>Go to main menu<a></p>");
		}
		else if(selection.equals("addNewEmperor")){		//If the user decided to add a new emperor, this part of the code enters this new date - emperor pair to the system. 
			String emperorName = request.getParameter("input");
			if(emperorName.isEmpty()){
				out.println("<p>Please enter a valid emperor name!</p>");
			}else{
				String queriedYear = request.getParameter("queriedYear");
				Integer intYear =Integer.parseInt(queriedYear);
				Melih_DatabaseConnection.useDatabase();
				boolean addedSuccessfully = Melih_DatabaseConnection.addData(new Melih_Data(emperorName, intYear));
				Melih_DatabaseConnection.orderTable();
				if(addedSuccessfully){
					out.println("<p>Your request has been added!</p>");
				}else{
					out.println("<p>You tried to add a replicate emperor! Please enter a valid (unique) emperor name.</p>");
				}
			}
			out.println("<p><a href=Melih?menu=main>Go to main menu<a></p>");
		}else if(selection.equals("unsave")){		//If the user wants the database to forget all the saves she/he made, this part of the servlet gets activated.
			Melih_DatabaseConnection.useDatabase();
			Melih_DatabaseConnection.unsaveSaved();
			Melih_DatabaseConnection.clearSaveHistory();
			out.println("<html>");
			out.println("<body>");
			out.println("<p>Your saved entries are unsaved.</p>");
			out.println("</html>");
			out.println("</body>");
			out.println("<p><a href=Melih?menu=main>Go to main menu<a></p>");
		}
		
		out.println("</body>");
		out.println("</html>");
	}	
}
