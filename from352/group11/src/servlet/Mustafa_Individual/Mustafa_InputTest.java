import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;
/**
 * Testing whether there is any country with a specific population.
 * @author Mustafa
 *
 */
public class Mustafa_InputTest {

	@Test
	public void testDoGetHttpServletRequestHttpServletResponse() {
		Integer myQuery = 74932641;//Turkey
		ArrayList<Mustafa_Data> queryData = Mustafa_DatabaseConnection.makeQuery(myQuery);
		boolean test = false;
		if (queryData.size()>0) test = true;
		assertTrue("Testing whether there is any country with a specific population.",test);
	}

}
