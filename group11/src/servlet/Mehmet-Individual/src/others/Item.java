package others;
/**
 * 
 * @author mehmet
 *	 It represents a row in database.
 */
public class Item {
	public String year, name;
	public Boolean checked;
	public Item(String s1, String s2, Boolean b){
		year = s1;
		name = s2;
		checked = b;
	}
	@Override
	public String toString() {
		return year + " -> " + name ;//s+ " : " + checked;
	}
}
