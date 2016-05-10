import static org.junit.Assert.*;

import org.junit.Test;

/**
* JUnit Class for testing setting up of Databases and tables.
* The tests encompass creation of DB and tables, dropping of DB
* and tables, ordering of tables and using the database. 
* Deniz_Data class.
*
* @author  Deniz Celik
* @version 1.0
* @since   05102016
*/
public class Deniz_DatabaseConnectionTest {

	@Test
	public void testDropDatabase() {
		boolean testDrop = Deniz_DatabaseConnection.dropDatabase();
		assertTrue("Testing dropDatabase()",testDrop);
	}

	@Test
	public void testCreateDatabase() {
		boolean testCreate = Deniz_DatabaseConnection.createDatabase();
		assertTrue("Testing createDatabase()",testCreate);
	}

	@Test
	public void testUseDatabase() {
		boolean testUse = Deniz_DatabaseConnection.useDatabase();
		assertTrue("Testing useDatabase()",testUse);
	}

	@Test
	public void testCreateTable() {
		boolean testCreateWiki = Deniz_DatabaseConnection.createTable(true);
		boolean testCreateUser = Deniz_DatabaseConnection.createTable(false);
		assertTrue("Testing createTable() for WikiData",testCreateWiki);
		assertTrue("Testing createTable() for UserData",testCreateUser);
	}

	@Test
	public void testDropTable() {
		boolean testDropWiki = Deniz_DatabaseConnection.createTable(true);
		boolean testDropUser = Deniz_DatabaseConnection.createTable(false);
		assertTrue("Testing dropTable() for WikiData",testDropWiki);
		assertTrue("Testing dropTable() for UserData",testDropUser);
	}

	@Test
	public void testOrderTable() {
		Deniz_DatabaseConnection.dropDatabase();
		Deniz_DatabaseConnection.createDatabase();
		Deniz_DatabaseConnection.useDatabase();
		Deniz_DatabaseConnection.createTable(true);
		Deniz_DatabaseConnection.createTable(false);
		boolean testOrderWiki = Deniz_DatabaseConnection.orderTable(true);
		boolean testOrderUser = Deniz_DatabaseConnection.orderTable(false);
		assertTrue("Testing orderTable() for WikiData",testOrderWiki);
		assertTrue("Testing orderTable() for UserData",testOrderUser);
	}
}
