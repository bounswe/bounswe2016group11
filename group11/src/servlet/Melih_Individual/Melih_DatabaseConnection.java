import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
* This class facilitates database operations. The methods in it 
* are called by the {@link Melih [Melih]} class and serve to access and update
* the database. 
* 
* In creation of this class, codes and instruction in 
* the following link have been partially utilized: 
* http://www.tutorialspoint.com/jdbc/jdbc-sample-code.htm
*
* @author  Melih Barsbey
* @version 1.0
* @since   2016-05-10 
*/

public class Melih_DatabaseConnection {
	// JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost:3306/melih_database";

	   //  Database credentials
	   static final String USER = "root";
	   static final String PASS = "";
	   
	   //  Necessary for connection to database
	   static Connection conn = null;
	   static Statement stmt = null; 

	/**
	 *  This method initializes the connection with the database if it
	 *  is not already established.
	 */
	   
	   private static void initialize(){
		   if(stmt != null && conn != null) return;
		   try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();	
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	   }
		/**
		 *  When called, this method drops the database if it already exists.
		 */
	   public static boolean dropDatabase(){
		   initialize();		   
		   String sql = "DROP DATABASE IF EXISTS melih_database";
		   System.out.println(sql);
		   
		  try {
			   PreparedStatement ps = conn.prepareStatement(sql);
			   ps.execute();
			   return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	   }
	   /**
		 *  When called, this method creates a database called 'melih_database' when called.
		 */
	   public static boolean createDatabase(){
		   initialize();		   
		   String sql = "CREATE DATABASE melih_database";
		   System.out.println(sql);
		   try {
			   PreparedStatement ps = conn.prepareStatement(sql);
			   ps.execute();
			   return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	   }
	   
	   /**
		 *  When called, this method selects the database 'melih_database' to be used.
		 */
	   	      
	   public static boolean useDatabase(){
		   initialize();		   
		   String sql = "USE melih_database";
		   System.out.println(sql);
		   try {
			   PreparedStatement ps = conn.prepareStatement(sql);
			   ps.execute();
			   return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	   }
	   /**
	    *  When called, this method creates the table 'melih_data' with three columns.
	    *  This table holds the data obtained from Wikidata.org.
	    */
	 	   	      
	   public static boolean createTable(){
		   initialize();		   
		   String sql = "CREATE TABLE melih_data (emperor varchar(30) PRIMARY KEY, date integer, isSelected boolean)";
		   System.out.println(sql);
		   try {
			   PreparedStatement ps = conn.prepareStatement(sql);
			   ps.execute();
			   return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	   }
	   
	   /**
	    *  When called, this method orders the table according to the data of ascension
	    */
	   
	   public static boolean orderTable(){
		   initialize();		   
		   String sql = "SELECT * FROM melih_data ORDER BY date";
		   System.out.println(sql);
		   try {
			   PreparedStatement ps = conn.prepareStatement(sql);
			   ps.execute();
			   return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	   }
	   /**
	    *  When called, this method marks the emperor held in the myData object
	    *  as saved.
	    *  
	    *  @param myData This parameter holds the name of the emperor and his date of
	    *  ascension to be marked as saved.
	    */
	   public static boolean saveData(Melih_Data myData){
		   initialize();		   
		   String sql = "UPDATE melih_data SET isSelected=true WHERE emperor = ?";
		   System.out.println(sql);
		   try {
			   PreparedStatement ps = conn.prepareStatement(sql);
			   ps.setString(1, String.valueOf(myData.emperor));
			   ps.execute();
			   return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	   }
	   /**
	    *  When called, this method lifts the marks of all emperors, marking them all as unsaved.
	    */
	   public static boolean unsaveSaved(){
		   initialize();		   
		   String sql = "UPDATE melih_data SET isSelected=false";
		   System.out.println(sql);
		   try {
			   PreparedStatement ps = conn.prepareStatement(sql);
			   ps.execute();
			   return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	   }
	   
	   /**
	    *  When called with a year, this method checks if there are any emperors
	    *  who ascended at that given year and returns their name(s) if there are any.
	    *  
	    *  @param queriedYear This is the year the user queried.
	    *  @return data The emperors whose year of ascension matches the user's query year are in this array 
	    */

	   public static ArrayList<Melih_Data> makeQuery(Integer queriedYear) {
		   initialize();
		   String sql = "SELECT * FROM melih_data WHERE date = " + queriedYear;
		   ResultSet rs;
		   ArrayList<Melih_Data> data = new ArrayList<Melih_Data>();
		   try {
			   rs = stmt.executeQuery(sql);
			   while(rs.next())
				   data.add(new Melih_Data(rs.getString("emperor"), rs.getInt("date"), rs.getBoolean("isSelected")));
			   rs.close();
		   } catch (SQLException e) {
			   e.printStackTrace();
		   }  
		   return data;
	   }
	   
	   /**
	    *  When called with a Melih_Data object containing emperor name and year,
	    *  this method attempts to add this to the database. It returns true 
	    *  if it successfully does so. It returns false if it has not 
	    *  succeeded doing so (e.g. because of a replicate emperor name)
	    *  
	    *  @param myData This is the Melih_Data object holding a name, year, and information about whether the emperor is marked for later review
	    *  @return boolean Whether the action succeeded or not. 
	    */
	   
	   public static boolean addData(Melih_Data myData){
		   initialize();		   
		   String sql = "INSERT INTO melih_data (emperor,date,isSelected) VALUES (?,?,?)";
		   System.out.println(sql);
		   try {
			   PreparedStatement ps = conn.prepareStatement(sql);
			   ps.setString(1, String.valueOf(myData.emperor));
			   ps.setInt(2, myData.date);
			   ps.setBoolean(3, myData.isSelected);
			   ps.execute();
			   return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	   }
	   /**
	    *  When called with a Melih_Data object containing all emperor names, 
	    *  ascension years, and marking information are returned to the 
	    *  user in an array.
	    *  
	    *  @return data This Melih_Data array includes all the emperor information. 
	    */

	   public static ArrayList<Melih_Data> getData() {
		   initialize();
		   String sql = "SELECT * FROM melih_data";
		   ResultSet rs;
		   ArrayList<Melih_Data> data = new ArrayList<Melih_Data>();
		   try {
			   rs = stmt.executeQuery(sql);
			   while(rs.next())
				   data.add(new Melih_Data(rs.getString("emperor"), rs.getInt("date"), rs.getBoolean("isSelected")));
			   rs.close();
		   } catch (SQLException e) {
			// TODO Auto-generated catch block
			   e.printStackTrace();
		   }  
		   return data;
	   }
	}
