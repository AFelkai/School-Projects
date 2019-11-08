public abstract class Person {
	
	String name;
	int id;
	
	Person(String identification, String fn, String ln) {
		
		name = fn + " " + ln;
		id = Integer.parseInt(identification);
		
	}
	
	public String getName(Object fn, Object ln) {
		
		String name = fn + " " + ln;
		
		return name;
	}
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}

}