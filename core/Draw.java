package core;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.HashSet;
import java.util.Set;

public class Draw 
{
	
	Model model;
	int lastAngle = 0;
	
	public Draw(Model model) {
		this.model = model;
	}

	public void drawTopView(Graphics2D g2d, int width, int height) {
		/*Initial rotation to 0 so runway doesn't become unaligned*/
		AffineTransform at = new AffineTransform();
		g2d.setTransform(at);
		
//		int runwayLength = model.selectedRunway.runwayLength;
//		int runwayWidth = model.selectedRunway.runwayWidth;
		
		Runway runway = new Runway(300, 240, 100);
		runway.setLogicalRunways(new LogicalRunway("09", runway, 3000, 3000, 3000, 2800, 400), new LogicalRunway("27", runway, 3000, 3000, 3000, 2800, 200));
		
		
		int runwayLength = 3000;
		int runwayWidth = 200;
		
		float scale = 0.8F * width / (runwayLength + runway.shortAngleLogicalRunway.stopwayLength + runway.longAngleLogicalRunway.stopwayLength);
		
		int adjustedRunwayLength = (int)(scale * runwayLength);
		int adjustedRunwayWidth = (int)(scale * runwayWidth);
		
		int runwayX = width/2 - adjustedRunwayLength/2; 
		int runwayY = height/2 - adjustedRunwayWidth/2;
		
		
		drawRunwayTop(g2d, runway, runwayX, adjustedRunwayLength, adjustedRunwayWidth, height/2, scale);
		drawLogicalRunwayMeasurementsTop(g2d, false, runway.shortAngleLogicalRunway, runwayX, adjustedRunwayLength, adjustedRunwayWidth, height/2, scale);
		
		Obstacle ob = model.selectedObstacle;
		/* Test obstacle */ ob = new Obstacle("Plane", 100, 500, 10); ob.setPosition(760, 20);
		if (ob != null) {
			drawObstacleTop(g2d, ob, runwayX, height/2, scale);
		}
		
//		drawMeasurement(g2d, scale, 1500, width /2, 600, 90, "TORA");
		LogicalRunway logRun = model.selectedLogicalRunway;
		/* Test logRun */ logRun = new LogicalRunway("test", new Runway(240,300,60), 3000, 3000, 3000, 2800, 200);
		if (logRun != null) {
			g2d.setColor(Color.LIGHT_GRAY);
			int adjustedStopwayLength = (int) (scale * logRun.stopwayLength);
//			g2d.fillRect(runwayX - adjustedStopwayLength, runwayY, adjustedStopwayLength, adjustedRunwayWidth);
//			g2d.fillRect(runwayX+adjustedRunwayLength, runwayY, adjustedStopwayLength, adjustedRunwayWidth);
			
			//TODO: Fix positioning of arrows
			
		}
	}
	
	private void drawRunwayTop(Graphics2D g2d, Runway runway, int runwayX, int runwayLength, int runwayWidth, int centerlineY, float scale) {
		
		g2d.setColor(Color.gray);
		g2d.fillRect(runwayX, centerlineY - runwayWidth/2, runwayLength, runwayWidth);
		
		/*Draw Runway designators*/
		String lowDesig = runway.shortAngleLogicalRunway.designator;
		String highDesig = runway.longAngleLogicalRunway.designator;
		
		g2d.setColor(Color.WHITE);
		
		AffineTransform at = new AffineTransform();
		
		Font gFont = g2d.getFont();
		/*Scale designator font*/
		g2d.setFont(new Font(gFont.getFontName(), gFont.getStyle(), (int)(80 * scale)));
		
		int lowDesigWidth = g2d.getFontMetrics().stringWidth(lowDesig);
		int highDesigWidth = g2d.getFontMetrics().stringWidth(highDesig);
		
		//Rotate text 90 deg
		at.setToRotation(90 * Math.PI/180, (int)(runwayX + runwayLength * 0.05), centerlineY - lowDesigWidth/2);
		g2d.setTransform(at);
		g2d.drawChars(lowDesig.toCharArray(), 0, lowDesig.length(), (int)(runwayX + runwayLength * 0.05), centerlineY - lowDesigWidth/2);
		
		//Rotate text -90 deg
		at.setToRotation(-90 * Math.PI/180, (int)(runwayX + runwayLength * 0.95), centerlineY + highDesigWidth/2);
		g2d.setTransform(at);
		g2d.drawChars(highDesig.toCharArray(), 0, highDesig.length(), (int)(runwayX + runwayLength * 0.95), centerlineY + highDesigWidth/2);
		
		//Reset rotation
		at.setToRotation(0);
		g2d.setTransform(at);
		
		/*Draw Stopways*/
		g2d.setColor(Color.LIGHT_GRAY);
		int adjustedLowStopwayLength = (int) (scale * runway.shortAngleLogicalRunway.stopwayLength);
		int adjustedHighStopwayLength = (int) (scale * runway.longAngleLogicalRunway.stopwayLength);
		g2d.fillRect(runwayX - adjustedHighStopwayLength, centerlineY - runwayWidth/2, adjustedHighStopwayLength, runwayWidth);
		g2d.fillRect(runwayX+runwayLength, centerlineY - runwayWidth/2, adjustedLowStopwayLength, runwayWidth);
		
		/*Label Stopways*/
		g2d.setColor(Color.BLACK);
		String stopwayLabel = "Stopway";
		
		/*Low Angle Stopway*/
		g2d.setFont(new Font(gFont.getFontName(), gFont.getStyle(), (int)(runway.shortAngleLogicalRunway.stopwayLength * 60 * scale/300)));
		int stopwayLabelWidth = g2d.getFontMetrics().stringWidth(stopwayLabel);
		int stopwayLabelHeight = g2d.getFontMetrics().getHeight();
		
		g2d.drawChars(stopwayLabel.toCharArray(), 0, stopwayLabel.length(), runwayX + runwayLength + adjustedLowStopwayLength/2 - stopwayLabelWidth/2, centerlineY + stopwayLabelHeight/2);
		
		/*High Angle Stopway*/
		g2d.setFont(new Font(gFont.getFontName(), gFont.getStyle(), (int)(runway.longAngleLogicalRunway.stopwayLength * 60 * scale/300)));
		stopwayLabelWidth = g2d.getFontMetrics().stringWidth(stopwayLabel);
		stopwayLabelHeight = g2d.getFontMetrics().getHeight();
		
		g2d.drawChars(stopwayLabel.toCharArray(), 0, stopwayLabel.length(), runwayX - adjustedHighStopwayLength/2 - stopwayLabelWidth/2, centerlineY + stopwayLabelHeight/2);
		
		
		//Reset font
		g2d.setFont(gFont);
	}
	
	private void drawLogicalRunwayMeasurementsTop(Graphics2D g2d, boolean lowAngle, LogicalRunway lr, int runwayX, int runwayLength, int runwayWidth, int centerlineY, float scale) {
		Set<String> addedLabels = new HashSet<String>();
		
		for(int i = 0; i < 4; i++) {
			String selectedLabel = "";
			int selectedValue = Integer.MAX_VALUE;
			if(lr.tora < selectedValue && !addedLabels.contains("TORA")) {
				selectedValue = lr.tora;
				selectedLabel = "TORA";
			}
			if(lr.toda < selectedValue && !addedLabels.contains("TODA")) {
				selectedValue = lr.toda;
				selectedLabel = "TODA";
			}
			if(lr.asda < selectedValue && !addedLabels.contains("ASDA")) {
				selectedValue = lr.asda;
				selectedLabel = "ASDA";
			}
			if(lr.lda < selectedValue && !addedLabels.contains("LDA")) {
				selectedValue = lr.lda;
				selectedLabel = "LDA";
			}
			addedLabels.add(selectedLabel);
			int arrowX = lowAngle ? (int)(runwayX + scale*selectedValue/2) : (int)(runwayX+runwayLength - scale*selectedValue/2);
			int arrowY = (int)(centerlineY + runwayWidth/2 + 150*(i+1)*scale);
			drawMeasurement(g2d, scale, selectedValue, arrowX, arrowY, 90, selectedLabel, (int)(150*(i+1)*scale), (int)(150*(i+1)*scale));
		}
	}
	
	private void drawObstacleTop(Graphics2D g2d, Obstacle ob, int runwayX, int centerlineY, float scale) {
		g2d.setColor(Color.WHITE);
		//TODO: Fix positioning of obstacle
		int obX = runwayX + (int)(scale * ob.distanceFromThreshold);
		int obY = centerlineY - (int)(scale * ob.distanceFromCenterline);
		int obLength = (int)(scale * ob.length);
		int obWidth = (int)(scale * ob.width);
		g2d.fillRect(obX, obY, obLength, obWidth);
		
		g2d.setColor(Color.BLACK);
		g2d.drawRect(obX, obY, obLength, obWidth);
		
		//Scale font
		Font gFont = g2d.getFont();
		g2d.setFont(new Font(gFont.getFontName(), gFont.getStyle(), (int)(ob.width * 60 * scale/100)));
		
		int stringWidth = g2d.getFontMetrics().stringWidth(ob.name);
		int fontHeight = g2d.getFontMetrics().getHeight();
		
		g2d.drawChars(ob.name.toCharArray(), 0, ob.name.length(), obX + obLength/2 - stringWidth/2, obY + obWidth/2 + fontHeight/2);
		
		//Reset font
		g2d.setFont(gFont);
	}

	public void drawSideView(Graphics2D g2d, int width, int height) {
		//g2d.clearRect(0, 0, 600, 600); //TODO:: need to change to size of panel and not remove text
		g2d.setColor(Color.green);
		LogicalRunway lrw = model.selectedLogicalRunway;
		int runwayLength = lrw.tora;
		/*
		this.runway = runway;
        this.designator = designator;
        this.tora = tora;
        this.toda = toda;
        this.asda = asda;
        this.lda = lda;
        this.displacedThreshold = tora - lda;
        this.stopwayLength = stopwayLength;
        this.clearwayLength = toda - tora;
		 */
		//TODO:: display Runway designator

		g2d.fillRect(20, 150 - 15 - 1, runwayLength, 17);
		g2d.drawRect(20 + runwayLength, 150 - 10, lrw.stopwayLength, 10);
		if (lrw.clearwayLength > 0) {
			g2d.drawRect(20 + runwayLength, 150 - 20, lrw.clearwayLength, 20);
		}
		g2d.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		g2d.drawString("TORA = " + lrw.tora, 20, 120);
		g2d.drawString("Stopway = " + lrw.stopwayLength, 20 + runwayLength, 100);
		g2d.drawString("Clearway = " + lrw.clearwayLength, 20 + runwayLength + lrw.stopwayLength, 80);
		g2d.dispose();
	}
	
	private void drawMeasurement(Graphics2D g2d, float scale, int measurementLength, int arrowX, int arrowY, int angle, String identifier, int extrapolation1, int extrapolation2) {
		double angleR = angle * Math.PI / 180;
		int adjustedLength = (int)(scale* measurementLength);
		int x = (int)(Math.sin(-angleR) * adjustedLength / 2);
		int y = (int)(Math.cos(-angleR) * adjustedLength / 2);
		
		g2d.setColor(Color.BLACK);
		/*Draw main line*/		
		g2d.drawLine(arrowX - x, arrowY - y, arrowX + x, arrowY + y);
		
		/*Draw Arrow Heads*/
		double headAngleR = (angle + 45) * Math.PI / 180;
		int headLength = (int)(adjustedLength * 0.025);
		int headX = (int)(Math.sin(headAngleR) * headLength);
		int headY = (int)(Math.cos(headAngleR) * headLength);
		g2d.drawLine(arrowX - x, arrowY - y, arrowX - x - headX, arrowY - y + headY);
		g2d.drawLine(arrowX + x, arrowY + y, arrowX + x + headX, arrowY + y - headY);
		
		headAngleR = -(angle + 45) * Math.PI / 180;
		headLength = (int)(adjustedLength * 0.025);
		headX = (int)(Math.cos(headAngleR) * headLength);
		headY = (int)(Math.sin(headAngleR) * headLength);
		
		g2d.drawLine(arrowX - x, arrowY - y, arrowX - x + headX, arrowY - y - headY);
		g2d.drawLine(arrowX + x, arrowY + y, arrowX + x - headX, arrowY + y + headY);
		
		/*Draw label*/
		String stringData = new String( ((identifier != null && !identifier.isEmpty()) ? identifier + ": " : "") + measurementLength + "m");
		g2d.drawChars(stringData.toCharArray(), 0, stringData.length(), arrowX, arrowY-2);
		
		/*Draw extrapolation lines*/
		int e1x = -(int)(Math.cos(-angleR) * extrapolation1);
		int e1y = -(int)(Math.sin(-angleR) * extrapolation1);
		g2d.drawLine(arrowX - x, arrowY - y, arrowX - x + e1x, arrowY - y - e1y);
		
		int e2x = -(int)(Math.cos(-angleR) * extrapolation2);
		int e2y = -(int)(Math.sin(-angleR) * extrapolation2);
		g2d.drawLine(arrowX + x, arrowY + y, arrowX + x + e2x, arrowY + y - e2y);
	}
}
