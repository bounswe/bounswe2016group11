import static org.junit.Assert.*;

import org.junit.Test;

/**
* This is a JUnit class which tests the constructors of the data class
* Melih_Data. Both constructors are tested within this test class.
*
* @author  Melih Barsbey
* @version 1.0
* @since   2016-05-10
*/  

public class Melih_DataTest {

	@Test
	public void testMelih_DataStringIntegerBoolean() {
		Melih_Data testFirstConstructor = new Melih_Data("Hadrian", 223, true);
		Integer testInt = new Integer(223);
		assertEquals("Hadrian",testFirstConstructor.emperor);
		assertEquals(testInt, testFirstConstructor.date);
		assertTrue(testFirstConstructor.isSelected);
	}

	@Test
	public void testMelih_DataStringInteger() {
		Melih_Data testSecondConstructor = new Melih_Data("Cassius", 127);
		Integer testInt = new Integer(127);
		assertEquals("Cassius",testSecondConstructor.emperor);
		assertEquals(testInt, testSecondConstructor.date);
		assertFalse(testSecondConstructor.isSelected);
	}

}
