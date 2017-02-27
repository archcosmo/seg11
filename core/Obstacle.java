package core;

public class Obstacle {

	int left; //Distance from start of runway to start of obstacle
	int right; //Distance from start of runway to end of obstacle
	int height;
	String name;
	
	public Obstacle(int left, int right, int height, String name) {
		this.left = left;
		this.right = right;
		this.height = height;
		this.name = name;
	}
}