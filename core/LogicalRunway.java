package core;

public class LogicalRunway {

	String designator;
    int stopwayLength, clearwayLength;
    int tora, toda, asda, lda;

    public LogicalRunway(String designator, int tora, int toda, int asda, int lda) {
        this.designator = designator;
        this.tora = tora;
        this.toda = toda;
        this.asda = asda;
        this.lda = lda;
		//TODO: calculate lengths
    }

    public LogicalRunway(String designator, int runwayLength, int stopwayLength, int clearwayLength) {
		this.designator = designator;
		this.tora = runwayLength;
		this.stopwayLength = stopwayLength;
		this.clearwayLength = clearwayLength;
		//TODO: calculate thresholds
	}
}
