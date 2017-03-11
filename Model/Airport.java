package Model;

import java.util.ArrayList;

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
	
	public ArrayList<Runway> getRunways() 
	{
		return runways;
	}
}