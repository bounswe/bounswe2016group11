package net.codejva;
//
public class Melih_Data {
	String emperor;
	Integer date;
	boolean isSelected;
	
	public Melih_Data(String c_emperor, Integer c_date, boolean c_isSelected){
		emperor = c_emperor;
		date = c_date;
		isSelected = c_isSelected;
	}
	
	public Melih_Data(String c_emperor, Integer c_date){
		emperor = c_emperor;
		date = c_date;
		isSelected = false;
	}
}
