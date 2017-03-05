package Model;

public class Obstacle {

	String name;
	int distanceFromThreshold; //Distance from start of runway to start of obstacle
	int distanceFromCenterline; //Distance from runway centre line to top of object in birds eye view, can be negative
	int width, length, height; //Length is along runway, width is only viewable from birs eye view
	
	public Obstacle(String name, int width, int length, int height) {
		this.name = name;
		this.width = width;
		this.length = length;
		this.height = height;
		this.distanceFromThreshold = 0;
		this.distanceFromCenterline = 0;
	}
	
	public void setPosition(int distanceFromThreshold, int distanceFromCenterline) {
		this.distanceFromThreshold = distanceFromThreshold;
		this.distanceFromCenterline = distanceFromCenterline;
	}
}