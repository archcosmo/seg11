package Model;

import java.awt.Color;
import java.security.InvalidParameterException;

public class ColourScheme {

	Color background,
		  runway,
		  runwaySide,
		  centerline,
		  designators,
		  threshold,
		  labels,
		  cga,
		  obstacle,
		  obstacleBorder,
		  obstacleLabel,
		  recalculatedThreshold,
		  recalculatedThresholdLabel,
		  clearwayBorders,
		  clearwaySide,
		  stopways,
		  stopwaySide;
	
	String name;
	
	public ColourScheme(String name, Color[] colours) {
		if(colours.length != 17)
			throw new InvalidParameterException("The colour scheme '"+name+"' expects 17 colours, however " + colours.length + " were given.");
		
		this.name = name;
		
		background = colours[0];
		runway = colours[1];
		runwaySide = colours[2];
		centerline = colours[3];
		designators = colours[4];
		threshold = colours[5];
		labels = colours[6];
		cga = colours[7];
		obstacle = colours[8];
		obstacleBorder = colours[9];
		obstacleLabel = colours[10];
		recalculatedThreshold = colours[11];
		recalculatedThresholdLabel = colours[12];
		clearwayBorders = colours[13];
		clearwaySide = colours[14];
		stopways = colours[15];
		stopwaySide = colours[16];
	}
	
	public String toString() { return this.name; }
	
	public static ColourScheme[] defaultThemes() {
		ColourScheme[] schemes = new ColourScheme[2];
		
		Color[] defaultColours = new Color[17];
		defaultColours[0] = new Color(200, 221, 242); //Background
		defaultColours[1] = Color.gray; //Runway
		defaultColours[2] = Color.gray; //Runway in Side View
		defaultColours[3] = new Color(220, 220, 220); //Runway Centerline
		defaultColours[4] = Color.WHITE; //Designator Labels
		defaultColours[5] = new Color(0, 0, 0, 150); //Threshold Marker
		defaultColours[6] = Color.BLACK; //Measurement Arrows and Labels
		defaultColours[7] = Color.CYAN; //Cleared and Graded Area
		defaultColours[8] = Color.WHITE; //Obstacle Background
		defaultColours[9] = Color.BLACK; //Obstacle Border
		defaultColours[10] = Color.BLACK; //Obstacle Label
		defaultColours[11] = new Color(255, 0, 0, 150); //Recalculated Threshold Marker
		defaultColours[12] = Color.RED; //Label for Recalculated Threshold
		defaultColours[13] = Color.GRAY; //Clearway Boxes
		defaultColours[14] = Color.GRAY; //Clearways in side view
		defaultColours[15] = Color.LIGHT_GRAY; //Stopway Blocks
		defaultColours[16] = Color.LIGHT_GRAY; //Stopway in side view
		schemes[0] = new ColourScheme("Default", defaultColours);
		
		Color[] highContrastColours = new Color[17];
		highContrastColours[0] = Color.BLACK; //Background
		highContrastColours[1] = Color.PINK; //Runway
		highContrastColours[2] = Color.RED; //Runway in Side View
		highContrastColours[3] = Color.GREEN; //Runway Centerline
		highContrastColours[4] = Color.BLACK; //Designator Labels
		highContrastColours[5] = new Color(255, 200, 0, 200); //Threshold Marker
		highContrastColours[6] = Color.WHITE; //Measurement Arrows and Labels
		highContrastColours[7] = Color.RED; //Cleared and Graded Area
		highContrastColours[8] = new Color(170, 0, 255); //Obstacle Background
		highContrastColours[9] = Color.WHITE; //Obstacle Border 
		highContrastColours[10] = Color.WHITE; //Obstacle Label
		highContrastColours[11] = new Color(0, 0, 255, 200); //Recalculated Threshold Marker
		highContrastColours[12] = Color.BLUE; //Label for Recalculated Threshold
		highContrastColours[13] = new Color(228, 209, 22); //Clearway Boxes
		highContrastColours[14] = new Color(228, 209, 22); //Clearways in side view
		highContrastColours[15] = new Color(0, 145, 166); //Stopway Blocks
		highContrastColours[16] = new Color(0, 145, 166); //Stopway in side view
		schemes[1] = new ColourScheme("High Contrast", highContrastColours);
		
		return schemes;
	}
}
