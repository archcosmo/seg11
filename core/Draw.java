package core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.*;
import java.awt.geom.Ellipse2D;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

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
		
		g2d.setColor(Color.gray);
		g2d.fillRect(width/2 - adjustedRunwayLength/2, height/2 - adjustedRunwayWidth/2, adjustedRunwayLength, adjustedRunwayWidth);
		
		
		drawMeasurement(g2d, scale, 1500, width /2, 600, 90, "TORA");
	}

	public void drawSideView(Graphics2D g) {
		//g2d.clearRect(0, 0, 600, 600); //TODO:: need to change to size of panel and not remove text
		g.setColor(Color.green);
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

		g.fillRect(20, 150 - 15 - 1, runwayLength, 17);
		g.drawRect(20 + runwayLength, 150 - 10, lrw.stopwayLength, 10);
		if (lrw.clearwayLength > 0) {
			g.drawRect(20 + runwayLength, 150 - 20, lrw.clearwayLength, 20);
		}
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		g.drawString("TORA = " + lrw.tora, 20, 120);
		g.drawString("Stopway = " + lrw.stopwayLength, 20 + runwayLength, 100);
		g.drawString("Clearway = " + lrw.clearwayLength, 20 + runwayLength + lrw.stopwayLength, 80);
		g.dispose();
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
		int headLength = (int)(adjustedLength * 0.1);
		int headX = (int)(Math.sin(headAngleR) * headLength);
		int headY = (int)(Math.cos(headAngleR) * headLength);
		g2d.drawLine(arrowX - x, arrowY - y, arrowX - x - headX, arrowY - y + headY);
		g2d.drawLine(arrowX + x, arrowY + y, arrowX + x + headX, arrowY + y - headY);
		
		headAngleR = -(angle + 45) * Math.PI / 180;
		headLength = (int)(adjustedLength * 0.1);
		headX = (int)(Math.cos(headAngleR) * headLength);
		headY = (int)(Math.sin(headAngleR) * headLength);
		
		g2d.drawLine(arrowX - x, arrowY - y, arrowX - x + headX, arrowY - y - headY);
		g2d.drawLine(arrowX + x, arrowY + y, arrowX + x - headX, arrowY + y + headY);
		
		/*Draw length number*/
		String stringData = new String( ((identifier != null && !identifier.isEmpty()) ? identifier + ": " : "") + measurementLength + "m");
		g2d.drawChars(stringData.toCharArray(), 0, stringData.length(), arrowX, arrowY);
	}
}
