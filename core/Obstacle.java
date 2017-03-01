package core;

public class Obstacle {

	String name;
	int xPos; //Distance from start of runway to start of obstacle
	int yPos; //Distance from runway centre line to top of object in birds eye view, can be negative
	int width, length, height; //Length is along runway, width is only viewable from birs eye view
	int fromC, fromL, fromR;
	
	public Obstacle(String name, int width, int length, int height) {
		this.name = name;
		this.width = width;
		this.length = length;
		this.height = height;
		this.xPos = 0;
		this.yPos = 0;
	}
	
	public void setPos(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}

	public void setFrom(int c, int l, int r) {
		this.fromC = c;
		this.fromL = l;
		this.fromR = r;
	}
}