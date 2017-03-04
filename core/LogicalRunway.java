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
    
    public boolean isReciprocalOf(String recipDesignator) {
    	Integer thisAngle = Integer.parseInt(designator.substring(0, designator.length() - 1));
    	Integer recipAngle = Integer.parseInt(recipDesignator.substring(0, recipDesignator.length() - 1));
    	
    	if(thisAngle + recipAngle == 36) {
    		if(designator.endsWith("C") && recipDesignator.endsWith("C"))
    			return true;
    		else if(designator.endsWith("L") && recipDesignator.endsWith("R"))
    			return true;
    		else if(designator.endsWith("R") && recipDesignator.endsWith("L"))
    			return true;
    	}
    	
    	return false;
    }
}
