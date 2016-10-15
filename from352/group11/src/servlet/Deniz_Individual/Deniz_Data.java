
/**
* Class for storing American Soccer Team data. 
* Used to save and access team objects from WikiData and MySQL.
*
* @author  Deniz Celik
* @version 1.0
* @since   05102016
*/
public class Deniz_Data implements Comparable<Deniz_Data>{
	private String team;
	private int year;
	
	/**
	 * Constructor for Deniz_Data that takes in a team name and inception year
	 * @param inputTeam Name of team
	 * @param inputYear Year of inception
	 */
	public Deniz_Data(String inputTeam, int inputYear){
		this.team = inputTeam;
		this.year = inputYear;
	}
	
	/**
	 * Getter for team variable
	 * @return Team Name
	 */
	public String getTeam() {
		return team;
	}
	
	/**
	 * Setter for team variable
	 * @param team New name for the team
	 */
	public void setTeam(String team) {
		this.team = team;
	}
	
	/**
	 * Getter for year variable
	 * @return
	 */
	public int getYear() {
		return year;
	}
	
	/**
	 * Setter for year variable
	 * @param year New year for the team
	 */
	public void setYear(int year) {
		this.year = year;
	}
	/**
	 * Implementation of compareTo for comparison of Deniz_Data objects
	 * @param o Deniz_Data object to be compared to
	 * @return a negative integer, zero, or a positive integer 
	 * 		   as this object is less than, equal to, or greater than the specified object.
	 */
	@Override
	public int compareTo(Deniz_Data o) {
		return Integer.valueOf(o.year).compareTo(Integer.valueOf(this.year));
	}
}