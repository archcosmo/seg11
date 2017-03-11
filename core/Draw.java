package core;

import java.awt.Color;
import java.awt.Graphics2D;

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

	public void drawSideView(Graphics2D g2d) 
	{
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
