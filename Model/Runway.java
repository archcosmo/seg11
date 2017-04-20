package Model;

public class Runway {

	public static final int DEFAULT_RESA = 240;
	public static final int DEFAULT_BLAST_ALLOWANCE = 300;
	public static final int STRIP_END = 60;
	String designator;
	LogicalRunway shortAngleLogicalRunway; //Logical runway with angle 0 to 17
	LogicalRunway longAngleLogicalRunway;
	int RESA, blastAllowance, stripEnd;
	int width, length;

	public Runway(int RESA, int blastAllowance, int stripEnd, int length, int width) {
		this.RESA = RESA;
		this.blastAllowance = blastAllowance;
		this.stripEnd = stripEnd;
		this.length = length;
		this.width = width;
	}

	public void setLogicalRunways(LogicalRunway shortAngleLogicalRunway, LogicalRunway longAngleLogicalRunway) {
		this.shortAngleLogicalRunway = shortAngleLogicalRunway;
		this.longAngleLogicalRunway = longAngleLogicalRunway;
		this.designator = shortAngleLogicalRunway.designator + "/" + longAngleLogicalRunway.designator;
	}
	
	public String getName() 
	{
		return designator;
	}
	
    public LogicalRunway lowAngle() {
    	return shortAngleLogicalRunway;
    }
    
    public LogicalRunway highAngle() {
    	return longAngleLogicalRunway;
    }
}