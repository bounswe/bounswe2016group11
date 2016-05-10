/**
* This is the class where the fundamental data definition is made for the individual implementation project Roman 
* Emperors and their Years of Ascension. 
*
* @author  Melih Barsbey
* @version 1.0
* @since   2016-05-10
*/

public class Melih_Data {
	String emperor;
	Integer date;
	boolean isSelected;
	
	/**
	* This is the constructor that accepts emperor name, date, and selected value.
	* @param c_emperor Emperor name.
	* @param c_date Date of ascension.
	* @param c_isSelected Whether the data is selected.
	*/
	public Melih_Data(String c_emperor, Integer c_date, boolean c_isSelected){
		emperor = c_emperor;
		date = c_date;
		isSelected = c_isSelected;
	}
	/**
	* This is the constructor that accepts emperor name, date; selection value is by default false.
	* @param c_emperor Emperor name.
	* @param c_date Date of ascension.
	*/
	public Melih_Data(String c_emperor, Integer c_date){
		emperor = c_emperor;
		date = c_date;
		isSelected = false;
	}
}
