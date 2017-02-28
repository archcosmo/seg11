package core;

public class Obstacle {

	String name;
	int left; //Distance from start of runway to start of obstacle
	int right; //Distance from start of runway to end of obstacle
	int height;

	//Distance from runway centre line to top/bottom of object in birds eye view, can be negative
	int bottomYPos;
	int topYPos;
	
	public Obstacle(String name, int left, int right, int height, int bottomYPos, int topYPos) {
		this.name = name;
		this.left = left;
		this.right = right;
		this.height = height;
		this.bottomYPos = bottomYPos;
		this.topYPos = topYPos;
	}
}