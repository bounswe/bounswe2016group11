/**
 * keeps wikidata objects
 * @author Mustafa
 *
 */
public class Mustafa_Data {
	String country;
	Integer population;
	boolean isSelected;
	
	public Mustafa_Data(String c_country, Integer c_population, boolean c_isSelected){
		country = c_country;
		population = c_population;
		isSelected = c_isSelected;
	}
	
	public Mustafa_Data(String c_country, Integer c_population){
		country = c_country;
		population = c_population;
		isSelected = false;
	}
}