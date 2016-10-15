import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Dogukan_DatabaseConnection {
	// JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost:3306/Dogukan_database";

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
	   

	   public static ArrayList<Dogukan_Data> makeQuery(String teamName) {
		   initialize();
		   String sql = "SELECT * FROM Dogukan_data WHERE team = \"" + teamName + "\"";
		   System.out.println(sql);
		   
		   ResultSet rs;
		   ArrayList<Dogukan_Data> data = new ArrayList<Dogukan_Data>();
		   try {
			   
			   rs = stmt.executeQuery(sql);
			   System.out.println(rs);
			   while(rs.next())
				   data.add(new Dogukan_Data(rs.getString("stadium"), rs.getString("team"), rs.getBoolean("isSelected")));
			   rs.close();
		   } catch (SQLException e) {
			// TODO Auto-generated catch block
			   e.printStackTrace();
		   }  
		   return data;
	   }
	   
	   public static boolean addData(String stadium, String team, Boolean isSelected){
		   initialize();		   
		   String sql = "INSERT INTO Dogukan_data (stadium,team,isSelected) VALUES (?,?,?)";
		   System.out.println(sql);
		   try {
			   PreparedStatement ps = conn.prepareStatement(sql);
			   ps.setString(1, String.valueOf(stadium));
			   ps.setString(2, team);
			   ps.setBoolean(3, isSelected);
			   ps.execute();
			   return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
	   }
	   
	   public static boolean removeData(){
		   initialize();
		   String sql = "Delete From Dogukan_data";
		   try {
			   PreparedStatement ps = conn.prepareStatement(sql);
			   ps.execute();
			   return true;
		   } catch (SQLException e) {
			   e.printStackTrace();
			   return false;
		   }
	   }
	   
	   public static ArrayList<Dogukan_Data> getData() {
		   initialize();
		   String sql = "SELECT * FROM Dogukan_data";
		   ResultSet rs;
		   ArrayList<Dogukan_Data> data = new ArrayList<Dogukan_Data>();
		   try {
			   rs = stmt.executeQuery(sql);
			   while(rs.next())
				   data.add(new Dogukan_Data(rs.getString("stadium"), rs.getString("team"), rs.getBoolean("isSelected")));
			   rs.close();
		   } catch (SQLException e) {
			// TODO Auto-generated catch block
			   e.printStackTrace();
		   }  
		   return data;
	   }
	}