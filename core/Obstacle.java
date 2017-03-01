package core;

public class Obstacle {

	String name;
	int xPos = 0; //Distance from start of runway to start of obstacle
	int yPos = 0; //Distance from runway centre line to top of object in birds eye view, can be negative
	int width, length, height;
	
	public Obstacle(String name, int width, int length, int height) {
		this.name = name;
		this.width = width;
		this.length = length;
		this.height = height;
	}
	
	public void setPos(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	int fromC, fromL, fromR;
	public void setFrom(int c, int l, int r) {
		this.fromC = c;
		this.fromL = l;
		this.fromR = r;
	}
}