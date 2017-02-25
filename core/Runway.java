import java.util.ArrayList

public class Runway {
	String name;
	ArrayList<Threshold> thresholds;
	
	public Runway(String name) {
		this.name = name;
		thresholds = new ArrayList<Threshold>();
	}
	
	public void addThreshold(int tora, int toda, int asda, int lda) {
		threshold.add(new Threshold(tora, toda, asda, lda));
	}
}