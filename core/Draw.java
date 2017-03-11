package core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.*;

public class Draw 
{
	
	Model model;
	
	public Draw(Model model) {
		this.model = model;
	}

	public void drawTopView(Graphics2D g2d, int width, int height) {
//		int runwayLength = model.selectedRunway.runwayLength;
//		int runwayWidth = model.selectedRunway.runwayWidth;
		
		int runwayLength = 3000;
		int runwayWidth = 200;
		
		float scale = 0.8F * width / runwayLength;
		
		int adjustedRunwayLength = (int)(scale * runwayLength);
		int adjustedRunwayWidth = (int)(scale * runwayWidth);
		
		int runwayX = width/2 - adjustedRunwayLength/2; 
		int runwayY = height/2 - adjustedRunwayWidth/2;
		
		g2d.setColor(Color.gray);
		g2d.fillRect(runwayX, runwayY, adjustedRunwayLength, adjustedRunwayWidth);

		Obstacle ob = model.selectedObstacle;
		/* Test obstacle */ ob = new Obstacle("Plane", 100, 500, 10); ob.setPosition(760, 20);
		if (ob != null) {
			g2d.setColor(Color.BLACK);
			//TODO: Fix positioning of obstacle
			int obX = runwayX + (int)(scale * ob.distanceFromThreshold);
			int obY = runwayY + adjustedRunwayWidth/2 - (int)(scale * ob.distanceFromCenterline);
			int obLength = (int)(scale * ob.length);
			int obWidth = (int)(scale * ob.width);
			g2d.fillRect(obX, obY, obLength, obWidth);
		}
		
//		drawMeasurement(g2d, scale, 1500, width /2, 600, 90, "TORA");
		LogicalRunway logRun = model.selectedLogicalRunway;
		/* Test logRun */ logRun = new LogicalRunway("test", new Runway(240,300,60), 3000, 3000, 3000, 2800, 200);
		if (logRun != null) {
			g2d.setColor(Color.LIGHT_GRAY);
			int adjustedStopwayLength = (int) (scale * logRun.stopwayLength);
			g2d.fillRect(runwayX - adjustedStopwayLength, runwayY, adjustedStopwayLength, adjustedRunwayWidth);
			g2d.fillRect(runwayX+adjustedRunwayLength, runwayY, adjustedStopwayLength, adjustedRunwayWidth);
			
			//TODO: Fix positioning of arrows
			drawMeasurement(g2d, scale, logRun.tora, width /2, 400, 90, "TORA");
			drawMeasurement(g2d, scale, logRun.toda, width /2, 425, 90, "TODA");
			drawMeasurement(g2d, scale, logRun.asda, width /2, 450, 90, "ASDA");
			drawMeasurement(g2d, scale, logRun.lda, width /2, 475, 90, "LDA");
		}
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
	
	private void drawMeasurement(Graphics2D g2d, float scale, int measurementLength, int arrowX, int arrowY, int angle, String identifier) {
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
		
		/*Draw length number*/
		String stringData = new String( ((identifier != null && !identifier.isEmpty()) ? identifier + ": " : "") + measurementLength + "m");
		g2d.drawChars(stringData.toCharArray(), 0, stringData.length(), arrowX, arrowY-2);
	}
}
