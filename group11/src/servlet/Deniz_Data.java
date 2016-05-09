public class Deniz_Data implements Comparable<Deniz_Data>{
	private String team;
	private int year;
	public Deniz_Data(String inputTeam, int inputYear){
		this.team = inputTeam;
		this.year = inputYear;
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
	@Override
	public int compareTo(Deniz_Data o) {
		// TODO Auto-generated method stub
		return Integer.valueOf(o.year).compareTo(Integer.valueOf(this.year));
	}
}