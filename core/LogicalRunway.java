package core;

public class LogicalRunway {

	String designator;
	public Runway runway;
    int stopwayLength, clearwayLength;
    public int tora, toda, asda, lda, displacedThreshold,stripEnd;

    public LogicalRunway(String designator, Runway runway, int tora, int toda, int asda, int lda, int displacedThreshold, int stopwayLength) {
        this.designator = designator;
        this.tora = tora;
        this.toda = toda;
        this.asda = asda;
        this.lda = lda;
        this.displacedThreshold = displacedThreshold;
        this.stopwayLength = stopwayLength;
        this.clearwayLength = toda - tora;
        this.stripEnd = 60;
		//TODO: calculate lengths
    }
    
    public void setStripEnd(int length) {
    	this.stripEnd = length;
    }
}
