package net.codejva;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
	   
	   public static boolean addUser(Melih_User user){
		   initialize();		   
		   String sql = "INSERT INTO melih_users (user_id, user_name) VALUES (?,?)";
		   System.out.println(sql);
		   try {
			   PreparedStatement ps = conn.prepareStatement(sql);
			   ps.setString(1, String.valueOf(user.user_id));
			   ps.setString(2, user.user_name);
			   ps.execute();
			   return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	   }
	   public static boolean addData(Melih_Data data){
		   initialize();		   
		   String sql = "INSERT INTO melih_data (country,capital,isSelected) VALUES (?,?,?)";
		   System.out.println(sql);
		   try {
			   PreparedStatement ps = conn.prepareStatement(sql);
			   ps.setString(1, String.valueOf(data.country));
			   ps.setString(2, data.capital);
			   ps.setBoolean(3, data.isSelected);
			   ps.execute();
			   return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	   }
	   
	   public static ArrayList<Melih_User> getUsers() {
		   initialize();
		   String sql = "SELECT * FROM melih_users";
		   ResultSet rs;
		   ArrayList<Melih_User> users = new ArrayList<Melih_User>();
		   try {
			   rs = stmt.executeQuery(sql);
			   while(rs.next())
				   users.add(new Melih_User(rs.getInt("user_id"), rs.getString("user_name")));
			   rs.close();
		   } catch (SQLException e) {
			// TODO Auto-generated catch block
			   e.printStackTrace();
		   }  
		   return users;
	   }
	   public static ArrayList<Melih_Data> getData() {
		   initialize();
		   String sql = "SELECT * FROM melih_data";
		   ResultSet rs;
		   ArrayList<Melih_Data> data = new ArrayList<Melih_Data>();
		   try {
			   rs = stmt.executeQuery(sql);
			   while(rs.next())
				   data.add(new Melih_Data(rs.getString("country"), rs.getString("capital"), rs.getBoolean("isSelected")));
			   rs.close();
		   } catch (SQLException e) {
			// TODO Auto-generated catch block
			   e.printStackTrace();
		   }  
		   return data;
	   }
	}
