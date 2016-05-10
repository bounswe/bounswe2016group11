import static org.junit.Assert.*;
import org.junit.*;

public class Melih_DatabaseConnectionTest {

	@Test
	public void testUseDatabase() {
		boolean testWhetherUse = Melih_DatabaseConnection.useDatabase();
		assertTrue("Testing whether we can use the chosen database.",testWhetherUse);
	}

	@Test
	public void testOrderTable() {
    	Melih_DatabaseConnection.dropDatabase();
		Melih_DatabaseConnection.createDatabase();
		Melih_DatabaseConnection.useDatabase();
		Melih_DatabaseConnection.createDataTable();
		boolean testWhetherOrder = Melih_DatabaseConnection.orderTable();
		assertTrue("Testing whether we can order the table in the chosen database.",testWhetherOrder);
	}

}
