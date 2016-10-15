public class Dogukan_Data {
	String stadium;
	String team;
	boolean isSelected;
	
	public Dogukan_Data(String c_stadium, String c_team, boolean c_isSelected){
		stadium = c_stadium;
		team = c_team;
		isSelected = c_isSelected;
	}
	
	public Dogukan_Data(String c_stadium, String c_team){
		stadium = c_stadium;
		team = c_team;
		isSelected = false;
	}
}