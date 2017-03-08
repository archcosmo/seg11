package core;

public class Runway {

	public static final int DEFAULT_RESA = 240;
	public static final int DEFAULT_BLAST_ALLOWANCE = 300;
	public static final int STRIP_END = 60;
	String designator;
	LogicalRunway shortAngleLogicalRunway; //Logical runway with angle 0 to 17
	LogicalRunway longAngleLogicalRunway;
	int RESA, blastAllowance, stripEnd;

	public Runway(int RESA, int blastAllowance, int stripEnd) {
		this.RESA = RESA;
		this.blastAllowance = blastAllowance;
		this.stripEnd = stripEnd;
	}

	public void setLogicalRunways(LogicalRunway shortAngleLogicalRunway, LogicalRunway longAngleLogicalRunway) {
		this.shortAngleLogicalRunway = shortAngleLogicalRunway;
		this.longAngleLogicalRunway = longAngleLogicalRunway;
		this.designator = shortAngleLogicalRunway.designator + "/" + longAngleLogicalRunway.designator;
	}
}