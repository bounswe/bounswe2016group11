package others;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
/**
 * 
 * @author mehmet
 *	Handles all jobs between database and java.  
 *	I benefitted from "http://www.vogella.com/tutorials/MySQLJava/article.html"
 */

public class DatabaseConnection {
	// JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost:3306/group11";

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
	    * 
	    * @param item is a row in database
	    * @return true if insertion is successful.
	    */
	   public static boolean addItem(Item item){
		   initialize();		   
		   String sql = "INSERT INTO mehmet (year, name, checked) VALUES (?,?,?)";
		  
		   try {
			   PreparedStatement ps = conn.prepareStatement(sql);
			   ps.setString(1, item.year);
			   ps.setString(2, item.name);
			   ps.setBoolean(3, item.checked);
			   ps.execute();
			   return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	   }
	   
	   /**
	    * 
	    * @param year
	    * @return the row that is related with the year given as a parameter.
	    */
	   public static Item getItem(String year) {
		   initialize();
		   String sql = "SELECT * FROM mehmet";
		   ResultSet rs;
		   ArrayList<Item> users = new ArrayList<Item>();
		   try {
			   rs = stmt.executeQuery(sql);
			   while(rs.next())
				   users.add(new Item(rs.getString("year"), rs.getString("name"),true));	//burasi degisebilir
			   rs.close();
		   } catch (SQLException e) {
			   e.printStackTrace();
		   }  
	
		   for(int i=users.size()-1;i>=0;i--)
			   if(users.get(i).year.startsWith(year))
				   return users.get(i);
		   return null;
	   }
	   
	   /**
	    * finds the rows that user saved for himself.
	    * @return rows that are checked by user at initialization.
	    */
	   public static ArrayList<Item> getCheckedList(){
		   
		   initialize();
		   ArrayList<Item> checkedList = new ArrayList<Item>();
		   String sql = "SELECT * FROM mehmet WHERE checked = true";
		   ResultSet rs;
		   try {
			rs = stmt.executeQuery(sql);
			while(rs.next())
				checkedList.add(new Item(rs.getString("year"), rs.getString("name"), rs.getBoolean("checked")));
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   return checkedList;
	   }
	   /**
	    * When a user click a checkbox then save it, that year is checked as true here.
	    * @param year the year of the row that is wanted to save.
	    */
	   public static void update(String year){
		   Item item = getItem(year);
		   String sql = "UPDATE mehmet SET checked = true WHERE year = \"" + item.year + "\"";
		   System.out.println(sql);
		   try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }
	   /**
	    * deletes all rows from table.
	    * @return true if deletion is successful.
	    */
	   public static boolean deleteDatabase(){
		   initialize();
		   String sql = "DELETE FROM mehmet";
		   try {
			stmt.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			return false;
		}
	   }
}
