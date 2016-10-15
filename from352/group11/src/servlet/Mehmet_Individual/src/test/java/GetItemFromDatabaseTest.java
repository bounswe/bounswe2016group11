import junit.framework.TestCase;
import others.DatabaseConnection;
import others.Item;

public class GetItemFromDatabaseTest extends TestCase{
	
	public void testGetFromDatabase(){
		Item item = DatabaseConnection.getItem("35");
		assertEquals("David Lean", item.name);
	}
}
