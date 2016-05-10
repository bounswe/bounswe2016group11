import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Class that establishes connection with the MySQL server and 
 * contains methods for creating database and tables, dropping
 * database and tables, adding new data and querying old data.
 * 
 * During the creation of this class, the following link was used
 * as a basis with modification and addition for project specific
 * methods:
 * 
 * http://www.tutorialspoint.com/jdbc/jdbc-sample-code.htm
 * The site was first accessed on May 5th, 2016
 * 
 * @author Deniz Celik
 * @version 1.0
 * @since 05102016
 */
public class Deniz_DatabaseConnection{
	// Database name and JDBC driver
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/deniz_database";
	// Credentials for database
	private static final String USER = "root";
	private static final String PASS = "";
	// Initialize sql connection and statement variables
	private static Connection con = null;
	private static Statement smt = null;
	
	/**
	 * Initialize connection with database if not already connected
	 */
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
	
	/**
	 * Helper method for executing a given sql statement.
	 * Only works for sql statements that are prepared and
	 * take no input.
	 * @param sql MySQL string to be executed
	 * @return  true if the statement was executed without error
	 * 			false if the statement was malformed or encountered
	 * 				  an error during execution
	 */
	private static boolean executeSQLStatement(String sql){
		//System.out.println(sql);
		try{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Drop deniz_database if it exists
	 * @return true if the statement was executed without error
	 * 		   false if the statement encountered an error during execution
	 */
	public static boolean dropDatabase(){
		initializeDB();
		String sql = "DROP DATABASE IF EXISTS deniz_database";
		return executeSQLStatement(sql);
	}
	
	/**
	 * Create deniz_database if it does not exist
	 * @return true if the statement was executed without error
	 * 		   false if the statement encountered an error during execution
	 */
	public static boolean createDatabase(){
		initializeDB();
		String sql = "CREATE DATABASE IF NOT EXISTS deniz_database";
		return executeSQLStatement(sql);
	}
	
	/**
	 * Tells MySQL to select deniz_database for any further SQL actions
	 * @return true if the statement was executed without error
	 * 		   false if the statement encountered an error during execution
	 */
	public static boolean useDatabase(){
		initializeDB();
		String sql = "USE deniz_database";
		return executeSQLStatement(sql);
	}
	
	/**
	 * Create a new table for storing WikiData or UserData depending on
	 * the value of isWiki.
	 * @param isWiki true for creation of deniz_wiki_data table
	 * 				 false for creation of deniz_user_data table
	 * @return true if the statement was executed without error
	 * 		   false if the statement encountered an error during execution
	 */
	public static boolean createTable(Boolean isWiki){
		initializeDB();
		String sql = "";
		if(isWiki){
			sql = "CREATE TABLE IF NOT EXISTS deniz_wiki_data ("
					   //+ "id BIGINT NOT NULL AUTO_INCREMENT,"
					   + "team varchar(255) NOT NULL, "
					   + "year INTEGER,"
					   + "PRIMARY KEY (team)"
					   //+ "PRIMARY KEY (id)"
					   + ")";
		} else {
			sql = "CREATE TABLE IF NOT EXISTS deniz_user_data ("
					   + "id BIGINT NOT NULL AUTO_INCREMENT,"
					   + "team varchar(255) NOT NULL, "
					   + "year INTEGER,"
					   //+ "PRIMARY KEY (team)"
					   + "PRIMARY KEY (id)"
					   + ")";
		}
		return executeSQLStatement(sql);
	}
	
	/**
	 * Drops the WikiData or UserData table from the database
	 * @param isWiki true for deletion of deniz_wiki_data table
	 * 				 false for deletion of deniz_user_data table
	 * @return true if the statement was executed without error
	 * 		   false if the statement encountered an error during execution
	 */
	public static boolean dropTable(Boolean isWiki){
		initializeDB();
		String sql = "";
		if(isWiki){
			sql = "DROP TABLE IF EXISTS deniz_wiki_data";
		} else {
			sql = "DROP TABLE IF EXISTS deniz_user_data";
		}
		return executeSQLStatement(sql);
	}
	
	/**
	 * Orders the WikiData or UserData table for cleaner results and querys
	 * @param isWiki true for ordering of deniz_wiki_data table
	 * 				 false for ordering of deniz_user_data table
	 * @return true if the statement was executed without error
	 * 		   false if the statement encountered an error during execution
	 */
	public static boolean orderTable(Boolean isWiki){
		initializeDB();
		String sql = "";
		if(isWiki){
			sql = "SELECT * FROM deniz_wiki_data ORDER BY year";
		} else {
			sql = "SELECT * FROM deniz_user_data ORDER BY year";
		}
		return executeSQLStatement(sql);
	}
	
	/**
	 * Gets all the data from the WikiData or UserData table
	 * @param isWiki true for getting from deniz_wiki_data table
	 * 				 false for getting from deniz_user_data table
	 * @return true if the statement was executed without error
	 * 		   false if the statement encountered an error during execution
	 */
	public static ArrayList<Deniz_Data> getData(Boolean isWiki){
		initializeDB();
		String sql = "";
		if(isWiki){
			sql = "SELECT * FROM deniz_wiki_data";
		} else{
			sql = "SELECT * FROM deniz_user_data";
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
		return results;
	}
	
	/**
	 * Adds data to the WikiData or UserData table
	 * @param isWiki true for adding to deniz_wiki_data table
	 * 				 false for adding to deniz_user_data table
	 * @return true if the statement was executed without error
	 * 		   false if the statement encountered an error during execution
	 */
	public static boolean addData(Deniz_Data data, Boolean isWiki){
		initializeDB();
		String sql = "";
		if(isWiki){
			sql = "INSERT IGNORE INTO deniz_wiki_data (team,year) VALUES (?,?)";
		} else {
			sql = "INSERT IGNORE INTO deniz_user_data (team,year) VALUES (?,?)";
		}
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, data.getTeam());
			ps.setInt(2, data.getYear());
			ps.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Removes given data from the userData table
	 * @param data Data object to remove from the table
	 * @return true if the statement was executed without error
	 * 		   false if the statement encountered an error during execution
	 */
	public static boolean removeData(Deniz_Data data){
		initializeDB();
		String sql = "DELETE FROM deniz_user_data WHERE team=? AND year=?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, data.getTeam());
			ps.setInt(2, data.getYear());
			ps.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Helper method to streamline adding to the UserData table
	 * @param data Data object to save to the userData table
	 * @return true if the statement was executed without error
	 * 		   false if the statement encountered an error during execution
	 */
	public static boolean saveData(Deniz_Data data){
		return addData(data,false);
	}
	
	/**
	 * Allows for querying of tables for a given year
	 * @param year Year to query in table
	 * @param isWiki true for deletion of deniz_wiki_data table
	 * 				 false for deletion of deniz_user_data table
	 * @return ArrayList of Deniz_Data that matches the given year query
	 */
	public static ArrayList<Deniz_Data> queryData(int year, Boolean isWiki){
		initializeDB();
		String sql = "";
		ResultSet res;
		ArrayList<Deniz_Data> results = new ArrayList<Deniz_Data>();
		if(isWiki){
			sql = "SELECT * FROM deniz_wiki_data WHERE year = " + Integer.toString(year);
		} else {
			sql = "SELECT * FROM deniz_user_data WHERE year = " + Integer.toString(year);
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
		return results;
	}
}