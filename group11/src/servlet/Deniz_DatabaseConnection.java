import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
	
	public static boolean dropDatabase(){
		initializeDB();
		return false;
	}
	public static boolean createDatabase(){
		initializeDB();
		return false;
	}
	public static boolean useDatabase(){
		initializeDB();
		return false;
	}
	public static boolean createTable(){
		initializeDB();
		return false;
	}
	public static boolean orderTable(){
		initializeDB();
		return false;
	}
}