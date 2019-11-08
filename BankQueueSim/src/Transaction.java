
public class Transaction {

	int id;
	String type;
	int duration;
	
	Transaction(int typeID, String tpe, int dur) {
		
		id = typeID;
		type = tpe;
		duration = dur;
		
	}
	
	public int getID() {
		return id;
	}
	
	public String getType() {
		return type;
	}
	
	public int getDuration() {
		return duration;
	}
}
