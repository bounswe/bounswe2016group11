/**
 * This is the code that provides database connection. This is kinda common for my group,Group 11, however there are some changes.
 * Reference for this code: http://www.tutorialspoint.com/jdbc/jdbc-sample-code.htm
 */

/**
 * @author Merve Cerit
 *
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Merve_DatabaseConnection {
	// JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost:3306/merve_database";

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
	  
	   public static boolean deleteDatabase(){
		   initialize();
		   String sql = "Delete * from wikidata";
		   try {
			stmt.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	   }

	   public static ArrayList<Merve_Data> getActors(String year) {
		   initialize();
		   String sql = "SELECT * FROM wikidata";
		   ResultSet rs;
		   ArrayList<Merve_Data> actors = new ArrayList<Merve_Data>();
		   System.out.println("year -> " + year);
		   try {
			   rs = stmt.executeQuery(sql);
			   while(rs.next()){
				   Merve_Data a = new Merve_Data(rs.getInt("wikidata_id"), rs.getString("ActorLabel")
						   , rs.getString("TwitterIDLabel"), rs.getString("BirthDateLabel"));
				   if(a.BirthDateLabel.startsWith(year)){
					   actors.add(a);
				   }
			   }
			   rs.close();
		   } catch (SQLException e) {
			// TODO Auto-generated catch block
			   e.printStackTrace();
		   }  
		   return actors;
	   }
	   
	  
	   public static boolean addActor(Merve_Data myData){
		   initialize();		   
		   String sql = "INSERT INTO wikidata(wikidata_id, ActorLabel, TwitterIDLabel, BirthDateLabel) VALUES (?,?,?,?)";
		   try {
			   PreparedStatement ps = conn.prepareStatement(sql);
			   ps.setInt(1, myData.wikidata_id);
			   ps.setString(2, myData.ActorLabel);
			   ps.setString(3, myData.TwitterIDLabel);
			   ps.setString(4, myData.BirthDateLabel);
			   ps.execute();
			   return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	   }
	
}
