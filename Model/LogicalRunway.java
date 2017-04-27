package Model;

public class LogicalRunway {

	public String designator;
	Runway runway;
	public int tora, toda, asda, lda, displacedThreshold, stopwayLength, clearwayLength;

    public LogicalRunway(String designator, Runway runway, int tora, int toda, int asda, int lda) {
    	this.runway = runway;
        this.designator = designator;
        this.tora = tora;
        this.toda = toda;
        this.asda = asda;
        this.lda = lda;
        this.displacedThreshold = tora - lda;
        this.stopwayLength = asda - tora;
        this.clearwayLength = toda - tora;
    }
    
    public Runway getParentRunway() {
    	return runway;
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
