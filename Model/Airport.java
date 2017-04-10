package Model;

import java.util.ArrayList;
import java.util.List;

public class Airport {
	String name;
	ArrayList<Runway> runways;
	
	public Airport(String name) {
		this.name = name;
		runways = new ArrayList<Runway>();
	}
	
	public void addRunway(Runway runway) {
		runways.add(runway);
	}
	
	public String getName() {
		return name;
	}

	public List<Runway> getRunways() {
		return runways;
	}
}