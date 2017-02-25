public class Airport {
	String name;
	ArrayList<Runway> runways;
	
	public Airport(String name) {
		this.name = name;
		runways = new ArrayList<Runway>();
	}
	
	public void addRunway(String runwayName) {
		runways.add(new Runway(runwayName));
	}
}