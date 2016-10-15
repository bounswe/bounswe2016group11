/**
 * This is data class file which includes data-specific information. 
 */

/**
 * @author Merve Cerit
 *
 */

public class Merve_Data {
	int wikidata_id;
	String ActorLabel;
	String BirthDateLabel, TwitterIDLabel;
	
	public Merve_Data(int id, String actor, String username, String birth){
		wikidata_id = id;
		ActorLabel = actor;
		BirthDateLabel = birth;
		TwitterIDLabel = username;
	}
	@Override
	public String toString() {
		return ActorLabel + " " + BirthDateLabel + " " + TwitterIDLabel;
	}
}
