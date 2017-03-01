package core;

public class LogicalRunway {

	String designator;
	public Runway runway;
    int stopwayLength, clearwayLength;
    public int tora, toda, asda, lda, displacedThreshold;

    public LogicalRunway(String designator, Runway runway, int tora, int toda, int asda, int lda, int stopwayLength) {
    	this.runway = runway;
        this.designator = designator;
        this.tora = tora;
        this.toda = toda;
        this.asda = asda;
        this.lda = lda;
        this.displacedThreshold = tora - lda;
        this.stopwayLength = stopwayLength;
        this.clearwayLength = toda - tora;
		//TODO: calculate lengths
    }
    
    public int getRESA() {
    	return runway.RESA;
    }
    
    public int getBlastAllowance() {
    	return runway.blastAllowance;
    }
    
    public int getStripEnd() {
    	return runway.stripEnd;
    }
}
