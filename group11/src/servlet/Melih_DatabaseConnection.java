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
	   
	   public static boolean createTable(){
		   initialize();		   
		   String sql = "CREATE TABLE melih_data (emperor varchar(30), date integer, isSelected boolean)";
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
