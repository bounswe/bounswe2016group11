public class Deniz_Data{
	private String team;
	private int year;
	private String country;
	public Deniz_Data(String inputTeam, int inputYear, String inputCountry){
		this.team = inputTeam;
		this.year = inputYear;
		this.country = inputCountry;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
}