package Model;

public class Runway {

	String designator;
	LogicalRunway shortAngleLogicalRunway; //Logical runway with angle 0 to 17
	LogicalRunway longAngleLogicalRunway;
	public int RESA, blastAllowance, stripEnd;
	public int width, length;

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
		setDesignator();
	}
	
	public String getName() 
	{
		return designator;
	}
	
	public void setDesignator() {
		this.designator = shortAngleLogicalRunway.designator + "/" + longAngleLogicalRunway.designator;
	}
	
    public LogicalRunway lowAngle() {
    	return shortAngleLogicalRunway;
    }
    
    public LogicalRunway highAngle() {
    	return longAngleLogicalRunway;
    }
}