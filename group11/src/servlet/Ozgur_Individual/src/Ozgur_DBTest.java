import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class Ozgur_DBTest {

	@Test
	public void testUseDatabase() {
		assertEquals(true, Ozgur_DB.useDatabase());
		
	}
	
	/*
	 * Please note that there is a tuple in my db as
	 * type ---> 3  
	 * query---> Christian Bale
	 * value---> The Fighter
	 * year ---> 2010
	 * 
	 * If it does not exist, the test would not pass
	 * */
	@Test
	public void testMakeQuery() {
		String[] query = new String[2];
		query[0] = "2010";
		query[1] = "Christian Bale";
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("The Fighter");
		assertEquals(expected, Ozgur_DB.makeQuery(query, 3));
	}

	@Test
	public void testAddData() {
		String[] query = new String[2];
		query[0] = "Christian Bale";
		ArrayList<String> data = new ArrayList<String>();
		data.add("The Kopek");
		assertEquals(true, Ozgur_DB.addData(data, 1, query));
	}

}
