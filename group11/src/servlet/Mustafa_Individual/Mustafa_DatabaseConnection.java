
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
/**
 * Common class that handles database connection.
 * Reference: http://www.tutorialspoint.com/jdbc/jdbc-sample-code.htm
 * Reference date: 03.05.2016
 * @author Mustafa
 *
 */
public class Mustafa_DatabaseConnection {
	// JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost:3306/mysql";

	   //  Database credentials
	   static final String USER = "root";
	   static final String PASS = "";
	   
	   //  Necessary for connection to database
	   static Connection conn = null;
	   static Statement stmt = null;
	   
	   private static void initialize(){
		   if(stmt != null && conn != null) return;
		   try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();	
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }
	   
	   /**
	    * Drops database
	    * @return
	    */
	   public static boolean dropDatabase(){
		   initialize();		   
		   String sql = "DROP DATABASE mysql";
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
	    * Creates database
	    * @return
	    */
	   public static boolean createDatabase(){
		   initialize();		   
		   String sql = "CREATE DATABASE mysql";
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
	    * Uses database
	    * @return
	    */
	   public static boolean useDatabase(){
		   initialize();		   
		   String sql = "USE mysql";
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
	    * Creates table into database
	    * @return
	    */
	   public static boolean createTable(){
		   initialize();		   
		   String sql = "CREATE TABLE mustafa_data (country varchar(30) PRIMARY KEY, population integer, isSelected boolean)";
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
	    * orders table
	    * @return
	    */
	   public static boolean orderTable(){
		   initialize();		   
		   String sql = "SELECT * FROM mustafa_data ORDER BY population";
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
	    * saves data to database
	    * @param myData
	    * @return
	    */
	   public static boolean saveData(Mustafa_Data myData){
		   initialize();		   
		   String sql = "UPDATE mustafa_data SET isSelected=true WHERE country = ?";
		   System.out.println(sql);
		   try {
			   PreparedStatement ps = conn.prepareStatement(sql);
			   ps.setString(1, String.valueOf(myData.country));
			   ps.execute();
			   return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	   }
	   
	   /**
	    * makes query
	    * @param queriedYear
	    * @return
	    */
	   public static ArrayList<Mustafa_Data> makeQuery(Integer queriedYear) {
		   initialize();
		   String sql = "SELECT * FROM mustafa_data WHERE population = " + queriedYear;
		   ResultSet rs;
		   ArrayList<Mustafa_Data> data = new ArrayList<Mustafa_Data>();
		   try {
			   rs = stmt.executeQuery(sql);
			   while(rs.next())
				   data.add(new Mustafa_Data(rs.getString("country"), rs.getInt("population"), rs.getBoolean("isSelected")));
			   rs.close();
		   } catch (SQLException e) {
			// TODO Auto-generated catch block
			   e.printStackTrace();
		   }  
		   return data;
	   }
	   
	   /**
	    * inserts data into database
	    * @param myData
	    * @return
	    */
	   public static boolean addData(Mustafa_Data myData){
		   initialize();		   
		   String sql = "INSERT INTO mustafa_data (country,population,isSelected) VALUES (?,?,?)";
		   System.out.println(sql);
		   try {
			   PreparedStatement ps = conn.prepareStatement(sql);
			   ps.setString(1, String.valueOf(myData.country));
			   ps.setInt(2, myData.population);
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
	    * gets data from database
	    * @return
	    */
	   public static ArrayList<Mustafa_Data> getData() {
		   initialize();
		   String sql = "SELECT * FROM mustafa_data";
		   ResultSet rs;
		   ArrayList<Mustafa_Data> data = new ArrayList<Mustafa_Data>();
		   try {
			   rs = stmt.executeQuery(sql);
			   while(rs.next())
				   data.add(new Mustafa_Data(rs.getString("country"), rs.getInt("population"), rs.getBoolean("isSelected")));
			   rs.close();
		   } catch (SQLException e) {
			// TODO Auto-generated catch block
			   e.printStackTrace();
		   }  
		   return data;
	   }
	}