package core;

import java.util.ArrayList;

public class Runway {
	String name;
	ArrayList<Threshold> thresholds;
	
	public Runway(String name) {
		this.name = name;
		thresholds = new ArrayList<Threshold>();
	}
	
	public void addThreshold(String designator, int tora, int toda, int asda, int lda) {
		thresholds.add(new Threshold(designator, tora, toda, asda, lda));
	}
	
	public void addThreshold(Threshold t) {
		thresholds.add(t);
	}
}