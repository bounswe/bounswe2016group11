import java.awt.Window.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

public class Ozgur_DB {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost:3306/";

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
		String sql = "DROP DATABASE ozgurdb";
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
		String sql = "CREATE DATABASE ozgurdb";
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
		String sql = "USE ozgurdb";
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
		String sql = "CREATE TABLE ozgurtable (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT , query VARCHAR(50), type INT NOT NULL , value VARCHAR(50), year INT)";
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

	/*
	 * 
	 * 
	 * */
	public static ArrayList<String> makeQuery(String[] query , int type) {
		initialize();
		String sql = "";
		if(type != 3){
			sql = "SELECT value FROM ozgurtable WHERE query = '" + query[0] + "' AND  type = " + type;
		}
		else{
			sql = "SELECT value FROM ozgurtable WHERE query = '" + query[1] + "' AND  type = " + type + " year = " + query[0];
		}
		ResultSet rs;
		ArrayList<String> data = new ArrayList<String>();
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next())
				data.add(rs.getString("value"));
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return data;
	}

	public static boolean addData(ArrayList<String> data, int type, String[] query){
		initialize();		   
		String sql = "INSERT INTO ozgurtable (value,query,year,type) VALUES (?,?,?,?)";
		System.out.println(sql);
		try {
			for(int i=0; i<data.size(); i++){
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, data.get(i));
				if(type != 3){
					ps.setString(2, query[0]);
					ps.setNull(3, Types.INTEGER );
				}
				else{
					ps.setString(2, query[1]);
					ps.setInt(3, Integer.parseInt(query[0]) );
				}
				ps.setInt(4, type);
				
				ps.execute();

			}
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
