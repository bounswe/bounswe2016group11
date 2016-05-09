import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Deniz_DatabaseConnection{
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/deniz_database";
	
	private static final String USER = "root";
	private static final String PASS = "";
	
	private static Connection con = null;
	private static Statement smt = null;
	
	private static void initializeDB(){
		if(smt!=null && con!=null){return;}
		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(DB_URL,USER,PASS);
			smt = con.createStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	private static boolean executeSQLStatement(String sql){
		System.out.println(sql);
		try{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean dropDatabase(){
		initializeDB();
		String sql = "DROP DATABASE IF EXISTS deniz_database";
		return executeSQLStatement(sql);
	}
	public static boolean createDatabase(){
		initializeDB();
		String sql = "CREATE DATABASE IF NOT EXISTS deniz_database";
		return executeSQLStatement(sql);
	}
	public static boolean useDatabase(){
		initializeDB();
		String sql = "USE deniz_database";
		return executeSQLStatement(sql);
	}
	public static boolean createTable(Boolean isWiki){
		initializeDB();
		String sql = "";
		if(true){
			sql = "CREATE TABLE IF NOT EXISTS deniz_wiki_data ("
					   + "id BIGINT NOT NULL AUTO_INCREMENT,"
					   + "team varchar(255) NOT NULL, "
					   + "year INTEGER,"
					   + "PRIMARY KEY (id)"
					   + ")";
		}
		return executeSQLStatement(sql);
	}
	
	public static boolean dropTable(Boolean isWiki){
		initializeDB();
		String sql = "";
		if(true){
			sql = "DROP TABLE IF EXISTS deniz_wiki_data";
		}
		return executeSQLStatement(sql);
	}
	public static boolean orderTable(Boolean isWiki){
		initializeDB();
		String sql = "";
		if(true){
			sql = "SELECT * FROM deniz_wiki_data ORDER BY year";
		}
		return executeSQLStatement(sql);
	}
	public static ArrayList<Deniz_Data> getData(Boolean isWiki){
		initializeDB();
		String sql = "";
		if(true){
			sql = "SELECT * FROM deniz_wiki_data";
		}
		ResultSet res;
		ArrayList<Deniz_Data> results = new ArrayList<Deniz_Data>();
		try {
			res = smt.executeQuery(sql);
			while(res.next()){
				results.add(new Deniz_Data(res.getString("team"),res.getInt("year")));
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean addWikiData(Deniz_Data data){
		initializeDB();
		String sql = "INSERT INTO deniz_wiki_data (team,year) VALUES (?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, data.getTeam());
			ps.setInt(2, data.getYear());
			ps.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public static boolean addUserData(Deniz_Data data){
		initializeDB();
		String sql = "INSERT INTO deniz_user_data (team,year) VALUES (?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, data.getTeam());
			ps.setInt(2, data.getYear());
			ps.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public static boolean saveData(Deniz_Data data){
		addUserData(data);
		return false;
	}
	public static ArrayList<Deniz_Data> queryData(int year, Boolean isWiki){
		initializeDB();
		String sql = "";
		ResultSet res;
		ArrayList<Deniz_Data> results = new ArrayList<Deniz_Data>();
		if(true){
			sql = "SELECT * FROM deniz_wiki_data WHERE year = " + Integer.toString(year);
		}
		try {
			res = smt.executeQuery(sql);
			while(res.next()){
				results.add(new Deniz_Data(res.getString("team"), res.getInt("year")));
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}