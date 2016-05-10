import static org.junit.Assert.*;

import org.junit.Test;


/**
* JUnit Class for testing constructor and getters and setters of 
* Deniz_Data class.
*
* @author  Deniz Celik
* @version 1.0
* @since   05102016
*/
public class Deniz_DataTest {

	@Test
	public void testDeniz_Data() {
		Deniz_Data constructorTest = new Deniz_Data("Earthquakes",1995);
		assertEquals("Earthquakes",constructorTest.getTeam());
		assertEquals(1995,constructorTest.getYear());
	}

	@Test
	public void testGetTeam() {
		Deniz_Data getTeamTest = new Deniz_Data("Earthquakes",1995);
		assertEquals("Earthquakes",getTeamTest.getTeam());
	}

	@Test
	public void testSetTeam() {
		Deniz_Data setTeamTest = new Deniz_Data("Earthquakes",1995);
		assertEquals("Earthquakes",setTeamTest.getTeam());
		setTeamTest.setTeam("Dynamo");
		assertEquals("Dynamo", setTeamTest.getTeam());
	}

	@Test
	public void testGetYear() {
		Deniz_Data getYearTest = new Deniz_Data("Earthquakes",1995);
		assertEquals(1995,getYearTest.getYear());
	}

	@Test
	public void testSetYear() {
		Deniz_Data setYearTest = new Deniz_Data("Earthquakes",1995);
		assertEquals(1995,setYearTest.getYear());
		setYearTest.setYear(2000);
		assertEquals(200, setYearTest.getYear());
	}

}
