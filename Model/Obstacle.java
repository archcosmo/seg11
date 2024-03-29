package Model;

public class Obstacle {

	String name;
	int distanceFromThreshold; //Distance from start of runway to start of obstacle
	int distanceFromLowAngleEndOfRunway;
	int distanceFromCenterline; //Distance from runway centre line to top of object in birds eye view, can be negative
	public int width, length, height; //Length is along runway, width is only viewable from birs eye view
	int fromC, fromL, fromR;
	
	public Obstacle(String name, int width, int length, int height) {
		this.name = name;
		this.width = width;
		this.length = length;
		this.height = height;
		this.distanceFromThreshold = 0;
		this.distanceFromCenterline = 0;
	}

	public void setPosition(int distanceFromThreshold, LogicalRunway lr, boolean lowAngleLR, boolean towardsObj, int distanceFromCenterline) {
		this.distanceFromThreshold = distanceFromThreshold;
		if(lowAngleLR && towardsObj)
			this.distanceFromLowAngleEndOfRunway = distanceFromThreshold + lr.displacedThreshold;
		else if(lowAngleLR && !towardsObj)
			this.distanceFromLowAngleEndOfRunway = distanceFromThreshold + lr.displacedThreshold - length;
		else if(!lowAngleLR && !towardsObj)
			this.distanceFromLowAngleEndOfRunway = lr.runway.length - distanceFromThreshold - lr.displacedThreshold;
		else
			this.distanceFromLowAngleEndOfRunway = lr.runway.length - distanceFromThreshold - lr.displacedThreshold - length;
		this.distanceFromCenterline = distanceFromCenterline;
	}
	
	public String getName() { return name; }
	
	public void setDistCenter(int x) { distanceFromCenterline = x; }
	public void setDistLowEnd(int x) { distanceFromLowAngleEndOfRunway = x; }
}