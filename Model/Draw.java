		package Model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import Application.Controller;

public class Draw
{

	Controller controller;
	private float zoom;
	private int topPanX, sidePanX, topPanY, sidePanY, topMouseX, topMouseY, sideMouseX, sideMouseY;
	private ColourScheme colourScheme;
	private boolean drawLabels, labelMeasurements, displayDistancesOnMeasurements, drawOrigMeasurements, 
					drawLegend, floatingLegend, floatingCompassAndDirection, drawCompass, drawDirection;

	public boolean setZoomFactor(float zf) { 
		if(zf > 0) {
			this.zoom = zf;
			return true;
		}
		return false;
	}
	int rangus = 0;
	public float getZoomFactor() { return this.zoom; }
	
	public void setTopPan(int panX, int panY) {
		topPanX += panX;
		topPanY += panY;
	}
	
	public void setSidePan(int panX, int panY) {
		sidePanX += panX;
		sidePanY += panY;
	}
	
	public void setTopMousePos(int x, int y) {
		topMouseX = x;
		topMouseY = y;
	}
	
	public void setSideMousePos(int x, int y) {
		sideMouseX = x;
		sideMouseY = y;
	}
	
	public void setColourScheme(ColourScheme scheme) {
		this.colourScheme = scheme;
	}
	
	public boolean setPreference(String pref, boolean val) {
		switch(pref) {
			case "drawLabels":
				drawLabels = val;
				break;
			case "labelMeasurements":
				labelMeasurements = val;
				break;
			case "displayDistancesOnMeasurements":
				displayDistancesOnMeasurements = val;
				break;
			case "drawOrigMeasurements":
				drawOrigMeasurements = val;
				break;
			case "drawLegend":
				drawLegend = val;
				break;
			case "floatingLegend":
				floatingLegend = val;
				break;
			case "floatingCompassAndDirection":
				floatingCompassAndDirection = val;
				break;
			case "drawCompass":
				drawCompass = val;
				break;
			case "drawDirection":
				drawDirection = val;
				break;
			
			default:
				return false;
		}
		
		return true;
	}
	
	public Draw(Controller controller) {
		this.controller = controller;
		this.zoom = 1.0F;
		this.topPanX = 0;
		this.topPanY = 0;
		
		//Draw Preferences
		drawLabels = true;
		labelMeasurements = true;
		displayDistancesOnMeasurements = false;
		drawOrigMeasurements = true;
		drawLegend = true;
		floatingLegend = true;
		floatingCompassAndDirection = false;
		drawCompass = true;
		drawDirection = true;

		//Set to Default theme
		this.colourScheme = ColourScheme.defaultThemes()[0];
	}

	public void drawTopView(Graphics2D g2d, int width, int height) {
		
		//Pan Limit Checks
		int maxPanX = Math.abs((int)((this.zoom * width) - width)/2);
		int maxPanY = Math.abs((int)((this.zoom * height) - height)/2);
		
		if(topPanX > maxPanX)
			topPanX = maxPanX;
		if(topPanX < -maxPanX)
			topPanX = -maxPanX;
		if(topPanY > maxPanY)
			topPanY = maxPanY;
		if(topPanY < -maxPanY)
			topPanY = -maxPanY;
		
		//Set Background Colour
		g2d.setColor(colourScheme.background);
		g2d.fillRect(0, 0, width, height);

		Runway runway = controller.selectedRunway;
		Obstacle ob = controller.selectedObstacle;

		if(runway!=null) {
			int runwayLength = runway.length;
			int runwayWidth = runway.width;
			int totalLength = runwayLength + Math.max(runway.shortAngleLogicalRunway.stopwayLength, runway.shortAngleLogicalRunway.clearwayLength)
					+ Math.max(runway.longAngleLogicalRunway.stopwayLength, runway.longAngleLogicalRunway.clearwayLength);

			float scale = (0.8F * width / totalLength);

			/*Show selected logical runway
			String selectedLogRun = "None";
			if (controller.getSelectedLogicalRunway() != null) {
				selectedLogRun = controller.getSelectedLogicalRunway().designator;
			}*/

			////////////////////////////////////////////////////////////////////////////
			///////Re-adjust scale and width to fit No Clearway/No Stopway labels if needed///////
			////////////////////////////////////////////////////////////////////////////

			Font tempFont = g2d.getFont();
			g2d.setFont(new Font(tempFont.getFontName(), tempFont.getStyle(), (int)(60 * scale)));
			int noClearwayLabelWidth = g2d.getFontMetrics().stringWidth("* No clearway");
			g2d.setFont(tempFont);

			if(runway.shortAngleLogicalRunway.stopwayLength < 10+noClearwayLabelWidth && runway.shortAngleLogicalRunway.clearwayLength < 10+noClearwayLabelWidth) {
				if(runway.longAngleLogicalRunway.stopwayLength < 10+noClearwayLabelWidth && runway.longAngleLogicalRunway.clearwayLength < 10+noClearwayLabelWidth)
					totalLength = runwayLength + 2*(10+noClearwayLabelWidth);
				else
					totalLength = runwayLength + 10+noClearwayLabelWidth +
							(runway.longAngleLogicalRunway.stopwayLength > runway.longAngleLogicalRunway.clearwayLength ? runway.longAngleLogicalRunway.stopwayLength : runway.longAngleLogicalRunway.clearwayLength);
			}
			else if(runway.longAngleLogicalRunway.stopwayLength < 10+noClearwayLabelWidth && runway.longAngleLogicalRunway.clearwayLength < 10+noClearwayLabelWidth)
				totalLength = runwayLength + 10+noClearwayLabelWidth +
						(runway.shortAngleLogicalRunway.stopwayLength > runway.shortAngleLogicalRunway.clearwayLength ? runway.shortAngleLogicalRunway.stopwayLength : runway.shortAngleLogicalRunway.clearwayLength);

			scale = (0.8F * width / totalLength);

			//////////////////////////////////////////////////////////////////////////////////////

			scale *= zoom;

			int adjustedRunwayLength = (int)(scale * runwayLength);
			int adjustedRunwayWidth = (int)(scale * runwayWidth);

			int runwayX = width/2 - adjustedRunwayLength/2 + topPanX;
			int centerLine = height/2 + topPanY;

//			if (ob != null) {
//				Runway recalculatedRunway = new Runway(runway.RESA, runway.blastAllowance, runway.stripEnd, runway.length, runway.width);
//				if(!controller.lowAngleRunway)
//					recalculatedRunway.setLogicalRunways(runway.shortAngleLogicalRunway, new LogicalRunway(runway.longAngleLogicalRunway.designator, recalculatedRunway, controller.recalculatedValues.get(0), controller.recalculatedValues.get(1), controller.recalculatedValues.get(2), controller.recalculatedValues.get(3), 0));
//				else
//					recalculatedRunway.setLogicalRunways(new LogicalRunway(runway.shortAngleLogicalRunway.designator, recalculatedRunway, controller.recalculatedValues.get(0), controller.recalculatedValues.get(1), controller.recalculatedValues.get(2), controller.recalculatedValues.get(3), 0), runway.longAngleLogicalRunway);
//				runway = recalculatedRunway;
//			}

			drawRunwayTop(g2d, runway, runwayX, adjustedRunwayLength, adjustedRunwayWidth, centerLine, scale);

			if (ob != null) {
				drawObstacleTop(g2d, ob, runwayX, centerLine, scale);
				drawRecalculatedValuesTop(g2d, controller.lowAngleRunway, ob, runway, runwayX, adjustedRunwayLength, adjustedRunwayWidth, centerLine, scale);
			}

			drawLogicalRunwayMeasurementsTop(g2d, controller.lowAngleRunway, runway, runwayX, adjustedRunwayLength, adjustedRunwayWidth, centerLine, scale);

			if(drawLegend)
				drawLegend(g2d, width, height, true);
			
			drawCompassAndDirection(g2d, runway, width, height, true);
		}
	}
	
	private void drawCompassAndDirection(Graphics2D g2d, Runway runway, int width, int height, boolean topView) {
		float scale = (1.0F * width / 698) * (floatingCompassAndDirection ? 1 : zoom);
		
		Font gFont = g2d.getFont();
		g2d.setFont(new Font(gFont.getFontName(), gFont.getStyle(), (int)(20 * scale)));
		g2d.setColor(colourScheme.labels);
		
		//Biggest Direction
		int drawWidth = g2d.getFontMetrics().stringWidth("Landing/Take-Off Direction: ") + (int)(50*scale);
		if(!drawDirection)
			drawWidth = g2d.getFontMetrics().stringWidth("Compass Heading: ") + (int)(60*scale);
		
		int localX = width/2 + (int)(339 * scale) - drawWidth + (floatingCompassAndDirection ? 0 : (topView ? topPanX : sidePanX));
		int localY = height/2 - (int)(240 * height * (floatingCompassAndDirection ? 1 : zoom) / 601) + (floatingCompassAndDirection ? 0 : (topView ? topPanY : sidePanY));
		
		
//		g2d.drawString("Logical Runway Selected: "+selectedLogRun, width/10, height/10);

//		g2d.drawString("Obstacle Distance From Centerline: " + (ob == null ? "N/A" : ob.distanceFromCenterline), width/10, height/10 + g2d.getFontMetrics().getHeight());
//		g2d.drawString("Obstacle Distance From Threshold: " + (ob == null ? "N/A" : ob.distanceFromThreshold), width/10, height/10 + g2d.getFontMetrics().getHeight()*2);

		if(drawDirection) {
			g2d.drawString("Landing/Take-Off Direction: ", localX, localY);
			int dirAngle = controller.lowAngleRunway ? 90 : -90;

			drawArrow(g2d, dirAngle, scale, localX + g2d.getFontMetrics().stringWidth("Landing/Take-Off Direction: ") + (dirAngle == -90 ? (int)(scale*50) : 0), localY - 7, 50);
		}
		
		/*Draw Compass*/
		if(drawCompass) {
			int angle = Integer.parseInt(runway.shortAngleLogicalRunway.designator.substring(0,2)) * 10;

			int lowAngle = -90 - angle;
			
			g2d.drawString("Compass Heading: ", localX, localY + (drawDirection ? g2d.getFontMetrics().getHeight() : 0));
			drawArrow(g2d, lowAngle, scale, localX + g2d.getFontMetrics().stringWidth("Compass Heading: ") + (lowAngle < 0 ? (int)(scale*50) : ((lowAngle == 0 || lowAngle == 180) ? (int)(scale*25) : 0)), localY + (drawDirection ? g2d.getFontMetrics().getHeight() : 0), 50);
		}
	}
	
	private boolean mouseInLegend(int legendX, int legendY, int width, int height, int mX, int mY) {
		return mX > legendX && mY > legendY && mX < legendX+width && mY < legendY+height;
	}
	
	private void drawLegend(Graphics2D g2d, int width, int height, boolean topView) {
		float scale = (1.3F * width / 698) * (floatingLegend ? 1 : zoom);
		
		int legendX = width/2 - (int)(329 * width * (floatingLegend ? 1 : zoom) / 698) + (floatingLegend ? 0 : (topView ? topPanX : sidePanX));
		int legendY = height/2 - (int)(260 * height * (floatingLegend ? 1 : zoom) / 601) + (floatingLegend ? 0 : (topView ? topPanY : sidePanY));

		if((topView && !mouseInLegend(legendX, legendY, (int)(190*scale), (int)(131*scale), topMouseX, topMouseY)) || (!topView && !mouseInLegend(legendX, legendY, (int)(190*scale), (int)(131*scale), sideMouseX, sideMouseY))) {

			g2d.setColor(new Color(255, 255, 255, 100));
			g2d.fillRect(legendX, legendY, (int)(190*scale), (int)(131*scale));

			int legendKeyX = legendX + (int)(8*scale);
			int legendKeyY = legendY + (int)(8*scale);

			drawLegendKey(g2d, scale, legendKeyX, legendKeyY, topView ? colourScheme.runway : colourScheme.runwaySide, "Runway");
			drawLegendKey(g2d, scale, legendKeyX, legendKeyY + (int)(15*scale), colourScheme.centerline, "Centerline");
			drawLegendKey(g2d, scale, legendKeyX, legendKeyY + (int)(30*scale), colourScheme.threshold, "Threshold Marker");
			drawLegendKey(g2d, scale, legendKeyX, legendKeyY + (int)(45*scale), colourScheme.cga, "Cleared And Graded Area");
			drawLegendKey(g2d, scale, legendKeyX, legendKeyY + (int)(60*scale), colourScheme.obstacle, "Obstacle");
			drawLegendKey(g2d, scale, legendKeyX, legendKeyY + (int)(75*scale), colourScheme.recalculatedThreshold, "Recalculated Threshold");
			drawLegendKey(g2d, scale, legendKeyX, legendKeyY + (int)(90*scale), topView ? colourScheme.clearwayBorders : colourScheme.clearwaySide, "Clearway");
			drawLegendKey(g2d, scale, legendKeyX, legendKeyY + (int)(105*scale), topView ? colourScheme.stopways : colourScheme.stopwaySide, "Stopway");
		}
	}
	
	private void drawLegendKey(Graphics2D g2d, float scale, int x, int y, Color col, String label) {
		g2d.setColor(col);
		g2d.fillRect(x, y, (int)(10*scale), (int)(10*scale));
		g2d.setColor(colourScheme.labels);
		g2d.drawRect(x, y, (int)(10*scale), (int)(10*scale));
		
		Font gFont = g2d.getFont();
		g2d.setFont(new Font(gFont.getFontName(), gFont.getStyle(), (int)(12*scale)));
		
		g2d.drawChars(label.toCharArray(), 0, label.length(), x + (int)(15*scale), y + (int)(10*scale));
	}

	private void drawArrow(Graphics2D g2d, int angle, float scale, int startX, int startY, int length) {
		
		double angleR = angle * Math.PI / 180;
		int endX = (int) (startX + scale*length * Math.sin(angleR));
		int endY = (int) (startY - scale*length * Math.cos(angleR));

		g2d.drawLine(startX, startY, endX, endY);

		double headAngle = angleR-(135* Math.PI / 180);
		int startHeadX = endX;
		int startHeadY = endY;
		int endHeadX   = (int) (endX + (scale*100.F*length/250.F) * Math.sin(headAngle));
		int endHeadY   = (int) (endY - (scale*100.F*length/250.F) * Math.cos(headAngle));

		g2d.drawLine(startHeadX, startHeadY, endHeadX, endHeadY);

		headAngle = (angleR-(225* Math.PI / 180));
		endHeadX   = (int) (endX + (scale*100.F*length/250.F) * Math.sin(headAngle));
		endHeadY   = (int) (endY - (scale*100.F*length/250.F) * Math.cos(headAngle));

		g2d.drawLine(startHeadX, startHeadY, endHeadX, endHeadY);
	}

	private void drawRunwayTop(Graphics2D g2d, Runway runway, int runwayX, int runwayLength, int runwayWidth, int centerlineY, float scale) {
		
		/*Draw Cleared and Graded*/
		g2d.setColor(colourScheme.cga);
		g2d.fillRect(runwayX-(int)(scale*60), centerlineY-runwayWidth/2-(int)(scale*75), runwayLength+(int)(scale*120), runwayWidth+(int)(scale*150));
		g2d.fillRect(runwayX+(int)(scale*300), centerlineY-runwayWidth/2-(int)(scale*105), runwayLength-(int)(scale*600), runwayWidth+(int)(scale*210));

		int[] triLeftX = new int[] {runwayX+(int)(scale*150), runwayX+(int)(scale*300), runwayX+(int)(scale*300)};
		int[] triRightX = new int[] {runwayX+runwayLength-(int)(scale*150), runwayX+runwayLength-(int)(scale*300), runwayX+runwayLength-(int)(scale*300)};
		int[] triBotY = new int[] {centerlineY+runwayWidth/2+(int)(scale*75), centerlineY+runwayWidth/2+(int)(scale*105), centerlineY+runwayWidth/2+(int)(scale*75)};
		int[] triTopY = new int[] {centerlineY-runwayWidth/2-(int)(scale*75), centerlineY-runwayWidth/2-(int)(scale*105), centerlineY-runwayWidth/2-(int)(scale*75)};

		g2d.fillPolygon(triLeftX, triTopY, 3);
		g2d.fillPolygon(triRightX, triTopY, 3);
		g2d.fillPolygon(triLeftX, triBotY, 3);
		g2d.fillPolygon(triRightX, triBotY, 3);
		
		/*Draw runway*/
		g2d.setColor(colourScheme.runway);
		g2d.fillRect(runwayX, centerlineY - runwayWidth/2, runwayLength, runwayWidth);
		
		/*Draw centerline*/
		g2d.setColor(colourScheme.centerline);
		int lineLength = runwayLength/21;
		int lineWidth = runwayWidth/10;
		int lineX;
		for (int i = 1; i<19; i=i+2) {
			lineX = (int)(runwayX + runwayLength * 0.05)+(i*lineLength);
			g2d.fillRect(lineX, centerlineY-lineWidth/2, lineLength, lineWidth);
		}

		/*Draw Clearways*/
		g2d.setColor(colourScheme.clearwayBorders);
		int adjustedLowClearwayLength = (int) (scale * runway.shortAngleLogicalRunway.clearwayLength);
		int adjustedHighClearwayLength = (int) (scale * runway.longAngleLogicalRunway.clearwayLength);
		int adjustedLowClearwayWidth = (int)(600*scale);	//TOD0:: Fix when clearwayWidth implemented
		int adjustedHighClearwayWidth = (int)(600*scale);

		if(adjustedHighClearwayLength > 0 && adjustedHighClearwayWidth > 0)
			g2d.drawRect(runwayX -adjustedHighClearwayLength, centerlineY-adjustedHighClearwayWidth/2, adjustedHighClearwayLength, adjustedHighClearwayWidth);

		if(adjustedLowClearwayLength > 0 && adjustedLowClearwayWidth > 0)
			g2d.drawRect(runwayX+runwayLength-1, centerlineY-adjustedLowClearwayWidth/2, adjustedLowClearwayLength, adjustedLowClearwayWidth);
		
		/*Draw Stopways*/
		g2d.setColor(colourScheme.stopways);
		int adjustedLowStopwayLength = (int) (scale * runway.shortAngleLogicalRunway.stopwayLength);
		int adjustedHighStopwayLength = (int) (scale * runway.longAngleLogicalRunway.stopwayLength);
		g2d.fillRect(runwayX - adjustedHighStopwayLength, centerlineY - runwayWidth/2, adjustedHighStopwayLength, runwayWidth);
		g2d.fillRect(runwayX+runwayLength, centerlineY - runwayWidth/2, adjustedLowStopwayLength, runwayWidth);
		
		/*Label Clearway*/
		g2d.setColor(colourScheme.labels);

		Font gFont = g2d.getFont();
		
		/*Label used if no clearway:*/
		String noClearwayLabel = "No clearway";
		Font noClearwayFont = new Font(gFont.getFontName(), gFont.getStyle(), (int)(60 * scale));
		g2d.setFont(noClearwayFont);
		int noClearwayLabelWidth = g2d.getFontMetrics().stringWidth(noClearwayLabel);
		int noClearwayLabelHeight = g2d.getFontMetrics().getHeight();

		String clearwayLabel = "Clearway";
		
		/*Low Angle Clearway*/
		g2d.setFont(new Font(gFont.getFontName(), gFont.getStyle(), (int)(runway.shortAngleLogicalRunway.clearwayLength * 60 * scale/300)));
		int clearwayLabelWidth = g2d.getFontMetrics().stringWidth(clearwayLabel);
		int clearwayLabelHeight = g2d.getFontMetrics().getHeight();

		if(drawLabels) {
			if(adjustedLowClearwayLength > 0 && adjustedLowClearwayWidth > 0)
				g2d.drawChars(clearwayLabel.toCharArray(), 0, clearwayLabel.length(), runwayX + runwayLength + adjustedLowClearwayLength/2 - clearwayLabelWidth/2, centerlineY-adjustedHighClearwayWidth/2 + 3*clearwayLabelHeight/4);
			else {
				g2d.setFont(noClearwayFont);
				g2d.drawChars(noClearwayLabel.toCharArray(), 0, noClearwayLabel.length(), runwayX + runwayLength + 10 + adjustedLowStopwayLength, centerlineY-runwayWidth/2 + noClearwayLabelHeight);
			}
		}
		
		/*High Angle Clearway*/
		g2d.setFont(new Font(gFont.getFontName(), gFont.getStyle(), (int)(runway.longAngleLogicalRunway.clearwayLength * 60 * scale/300)));
		clearwayLabelWidth = g2d.getFontMetrics().stringWidth(clearwayLabel);
		clearwayLabelHeight = g2d.getFontMetrics().getHeight();

		if(drawLabels) {
			if(adjustedHighClearwayLength > 0 && adjustedHighClearwayWidth > 0)
				g2d.drawChars(clearwayLabel.toCharArray(), 0, clearwayLabel.length(), runwayX - adjustedHighClearwayLength/2 - clearwayLabelWidth/2, centerlineY-adjustedLowClearwayWidth/2 + 3*clearwayLabelHeight/4);
			else {
				g2d.setFont(noClearwayFont);
				g2d.drawChars(noClearwayLabel.toCharArray(), 0, noClearwayLabel.length(), runwayX - noClearwayLabelWidth - 10 - adjustedHighStopwayLength, centerlineY-runwayWidth/2 + noClearwayLabelHeight);
			}
		}
		
		/*Label Stopways*/
		g2d.setColor(colourScheme.labels);
		
		/*Label used if no stopway:*/
		String noStopwayLabel = "No stopway";
		Font noStopwayFont = new Font(gFont.getFontName(), gFont.getStyle(), (int)(60 * scale));
		g2d.setFont(noClearwayFont);
		int noStopwayLabelWidth = g2d.getFontMetrics().stringWidth(noStopwayLabel);

		String stopwayLabel = "Stopway";
		
		/*Low Angle Stopway*/
		g2d.setFont(new Font(gFont.getFontName(), gFont.getStyle(), (int)(runway.shortAngleLogicalRunway.stopwayLength * 60 * scale/300)));
		int stopwayLabelWidth = g2d.getFontMetrics().stringWidth(stopwayLabel);
		int stopwayLabelHeight = g2d.getFontMetrics().getHeight();

		if(drawLabels) {
			if(adjustedLowStopwayLength > 0)
				g2d.drawChars(stopwayLabel.toCharArray(), 0, stopwayLabel.length(), runwayX + runwayLength + adjustedLowStopwayLength/2 - stopwayLabelWidth/2, centerlineY + stopwayLabelHeight/2);
			else {
				g2d.setFont(noStopwayFont);
				g2d.drawChars(noStopwayLabel.toCharArray(), 0, noStopwayLabel.length(), runwayX + runwayLength + 10 + adjustedLowStopwayLength, centerlineY-runwayWidth/2 + 2*noClearwayLabelHeight);
			}
		}
		
		/*High Angle Stopway*/
		g2d.setFont(new Font(gFont.getFontName(), gFont.getStyle(), (int)(runway.longAngleLogicalRunway.stopwayLength * 60 * scale/300)));
		stopwayLabelWidth = g2d.getFontMetrics().stringWidth(stopwayLabel);
		stopwayLabelHeight = g2d.getFontMetrics().getHeight();

		if(drawLabels) {
			if(adjustedHighStopwayLength > 0)
				g2d.drawChars(stopwayLabel.toCharArray(), 0, stopwayLabel.length(), runwayX - adjustedHighStopwayLength/2 - stopwayLabelWidth/2, centerlineY + stopwayLabelHeight/2);
			else {
				g2d.setFont(noStopwayFont);
				g2d.drawChars(noStopwayLabel.toCharArray(), 0, noStopwayLabel.length(), runwayX - noStopwayLabelWidth - 10 - adjustedHighStopwayLength, centerlineY-runwayWidth/2 + 2*noClearwayLabelHeight);
			}
		}
		
		/*Draw Runway designators*/
		String lowDesig = runway.shortAngleLogicalRunway.designator;
		String highDesig = runway.longAngleLogicalRunway.designator;

		g2d.setColor(colourScheme.designators);

		/*Scale designator font*/
		g2d.setFont(new Font(gFont.getFontName(), gFont.getStyle(), (int)(80 * scale)));

		int lowDesigWidth = g2d.getFontMetrics().stringWidth(lowDesig);
		int highDesigWidth = g2d.getFontMetrics().stringWidth(highDesig);

		
		
		g2d.drawChars(lowDesig.toCharArray(), 0, lowDesig.length(), (int)(runwayX + runwayLength * 0.05) + lowDesigWidth/2, centerlineY + g2d.getFont().getSize()/2);
		g2d.drawChars(highDesig.toCharArray(), 0, highDesig.length(), (int)(runwayX + runwayLength * 0.95) - highDesigWidth/2, centerlineY + g2d.getFont().getSize()/2);

		//Reset font
		g2d.setFont(gFont);
	}

	private void drawLogicalRunwayMeasurementsTop(Graphics2D g2d, boolean lowAngle, Runway runway, int runwayX, int runwayLength, int runwayWidth, int centerlineY, float scale) {
		Set<String> addedLabels = new HashSet<String>();

		LogicalRunway lr = lowAngle ? runway.shortAngleLogicalRunway : runway.longAngleLogicalRunway;

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
			
			/*LDA Positioned from threshold*/
			if(selectedLabel.equals("LDA")) {
				int adjustedDisplacement = (int)(scale*lr.displacedThreshold);
				arrowX = lowAngle ? (int)(runwayX+adjustedDisplacement + scale*selectedValue/2) : (int)(runwayX+runwayLength-adjustedDisplacement - scale*selectedValue/2);
			}
			int arrowY = (int)(centerlineY + runwayWidth/2 + 150*(i+1)*scale);
			if(drawOrigMeasurements)
				drawMeasurement(g2d, scale, selectedValue, arrowX, arrowY, 90, selectedLabel, (int)(150*(i+1)*scale), (int)(150*(i+1)*scale));
		}
		
		/*Displaced Threshold*/
		int adjustedDisplacement = (int)(scale*lr.displacedThreshold);
		int displacedThreshWidth = runwayLength/100;
		int displacementX = lowAngle ? runwayX+adjustedDisplacement-displacedThreshWidth/2 : runwayX+runwayLength-adjustedDisplacement-displacedThreshWidth/2;
		
		/*Make transparent so threshold designators can be read*/
		g2d.setColor(colourScheme.threshold);
		g2d.fillRect(displacementX, centerlineY - runwayWidth/2, displacedThreshWidth, runwayWidth);
		String displacedLabel = "Displaced Threshold";

		Font gFont = g2d.getFont();
		g2d.setColor(colourScheme.labels);
		g2d.setFont(new Font(gFont.getFontName(), Font.PLAIN, (int)(60 * scale)));

		if(drawLabels) {
			g2d.drawChars(displacedLabel.toCharArray(), 0, displacedLabel.length(), displacementX, centerlineY-runwayWidth/2-5);
		}
		
		//Reset Font
		g2d.setFont(gFont);
	}

	private void drawRecalculatedValuesTop(Graphics2D g2d, boolean lowAngle, Obstacle ob, Runway runway, int runwayX, int runwayLength, int runwayWidth, int centerlineY, float scale) {
		Set<String> addedLabels = new HashSet<String>();

		LogicalRunway lr = new LogicalRunway("", runway, controller.recalculatedValues.get(0), controller.recalculatedValues.get(1),controller.recalculatedValues.get(2), controller.recalculatedValues.get(3));
		
		/*Displaced Threshold*/
		int displacedThreshWidth = runwayLength/100;
		int displacementX = (lowAngle && !controller.towardsSelectedLR) || (!lowAngle && controller.towardsSelectedLR) ? runwayX + runwayLength - (int)(scale * lr.tora) : runwayX+(int)(scale*lr.tora);

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
			int arrowX = (lowAngle && !controller.towardsSelectedLR) || (!lowAngle && controller.towardsSelectedLR) ? (int)(displacementX + scale*selectedValue/2) : (int)(displacementX - scale*selectedValue/2);
			
			/*LDA Positioned from threshold*/
			if(selectedLabel.equals("LDA")) {
				//arrowX = (lowAngle && !controller.towardsSelectedLR) || (!lowAngle && controller.towardsSelectedLR) ? (int)(displacementX + Math.abs(adjustedDisplacement) + scale*selectedValue/2) : (int)(displacementX - Math.abs(adjustedDisplacement)- scale*selectedValue/2);
				arrowX = (lowAngle && !controller.towardsSelectedLR) || (!lowAngle && controller.towardsSelectedLR) ? (int)(displacementX + scale*lr.tora - scale*selectedValue/2) : (int)(displacementX - scale*lr.tora + scale*selectedValue/2);
			}
			int arrowY = (int)(centerlineY - runwayWidth/2 - 150*(i+1)*scale);
			drawMeasurement(g2d, scale, selectedValue, arrowX, arrowY, 90, "Recalculated " + selectedLabel, -(int)(150*(i+1)*scale), -(int)(150*(i+1)*scale));
		}

		int threshX = (lowAngle && !controller.towardsSelectedLR) || (!lowAngle && controller.towardsSelectedLR) ? (int)(displacementX + scale*lr.tora - scale*lr.lda) : (int)(displacementX - scale*lr.tora + scale*lr.lda);

		//LogicalRunway selectedLR = lowAngle ? runway.shortAngleLogicalRunway : runway.longAngleLogicalRunway;

//		if(controller.towardsSelectedLR) {
//			int measureX = (lowAngle && !controller.towardsSelectedLR) || (!lowAngle && controller.towardsSelectedLR) ? (int)(runwayX + scale*(ob.distanceFromLowAngleEndOfRunway + ob.length) ) : (int)(runwayX + scale*ob.distanceFromLowAngleEndOfRunway) ;
//			
//			drawMeasurement(g2d, scale, 50*ob.height, measureX + ((lowAngle && !controller.towardsSelectedLR) || (!lowAngle && controller.towardsSelectedLR) ? (int)(ob.height*50*scale/2) : -(int)(ob.height*50*scale/2)) , (int)(centerlineY), 90, "ALS", 0, 0);
//			drawMeasurement(g2d, scale, runway.stripEnd, measureX + (int)(50*scale*ob.height) + ((lowAngle && !controller.towardsSelectedLR) || (!lowAngle && controller.towardsSelectedLR) ? (int)(runway.stripEnd*scale/2) : -(int)(runway.stripEnd*scale/2)) , (int)(centerlineY), 90, "Strip End", 0, 0);
//		}
//		else {
//			int RESA_StripEnd = runway.RESA + runway.stripEnd;
//			int blast_DThreshold = runway.blastAllowance + selectedLR.displacedThreshold;
//			
//			int measureX = (lowAngle && !controller.towardsSelectedLR) || (!lowAngle && controller.towardsSelectedLR) ? (int)(runwayX + scale*(ob.distanceFromLowAngleEndOfRunway + ob.length) ) : (int)(runwayX + scale*ob.distanceFromLowAngleEndOfRunway) ;
//			
//			if (RESA_StripEnd > blast_DThreshold) {
//				drawMeasurement(g2d, scale, runway.RESA, measureX + ((lowAngle && !controller.towardsSelectedLR) || (!lowAngle && controller.towardsSelectedLR) ? (int)(runway.RESA*scale/2) : -(int)(runway.RESA*scale/2)) , (int)(centerlineY), 90, "RESA", -(int)(150*scale), -(int)(150*scale));
//				drawMeasurement(g2d, scale, runway.stripEnd, measureX + (int)(scale*runway.RESA) + ((lowAngle && !controller.towardsSelectedLR) || (!lowAngle && controller.towardsSelectedLR) ? (int)(runway.stripEnd*scale/2) : -(int)(runway.stripEnd*scale/2)) , (int)(centerlineY - runwayWidth/2 - 150*scale), 90, "Strip End", -(int)(150*scale), -(int)(150*scale));
//			} else {
//				drawMeasurement(g2d, scale, runway.blastAllowance, measureX + ((lowAngle && !controller.towardsSelectedLR) || (!lowAngle && controller.towardsSelectedLR) ? (int)(runway.blastAllowance*scale/2) : -(int)(runway.blastAllowance*scale/2)) , (int)(centerlineY), 90, "Blast Allowance", 0, 0);
//			} 
//		}
		
		/*Make transparent so threshold designators can be read*/
		g2d.setColor(colourScheme.recalculatedThreshold);
		g2d.fillRect(threshX-displacedThreshWidth/2, centerlineY - runwayWidth/2, displacedThreshWidth, runwayWidth);
		String displacedLabel = "Recalculated Displaced Threshold";

		Font gFont = g2d.getFont();
		g2d.setColor(colourScheme.recalculatedThresholdLabel);
		g2d.setFont(new Font(gFont.getFontName(), Font.PLAIN, (int)(60 * scale)));

		if(drawLabels) {
			g2d.drawChars(displacedLabel.toCharArray(), 0, displacedLabel.length(), threshX, centerlineY+runwayWidth/2+g2d.getFontMetrics().getHeight());
		}
		
		//Reset Font
		g2d.setFont(gFont);
	}

	private void drawObstacleTop(Graphics2D g2d, Obstacle ob, int runwayX, int centerlineY, float scale) {
		g2d.setColor(colourScheme.obstacle);
		int obX = runwayX + (int)(scale * ob.distanceFromLowAngleEndOfRunway);
		int obY = centerlineY - (int)(scale * ob.distanceFromCenterline);
		int obLength = (int)(scale * ob.length);
		int obWidth = (int)(scale * ob.width);
		g2d.fillRect(obX, obY, obLength, obWidth);

		g2d.setColor(colourScheme.obstacleBorder);
		g2d.drawRect(obX, obY, obLength, obWidth);

		//Scale font
		Font gFont = g2d.getFont();
		g2d.setFont(new Font(gFont.getFontName(), gFont.getStyle(), (int)(ob.width * 60 * scale/100)));

		String planeLabel = ob.name;

		int stringWidth = g2d.getFontMetrics().stringWidth(planeLabel);
		int fontHeight = g2d.getFontMetrics().getHeight();
		boolean shortenedLabel = false;

		while(stringWidth > obLength && planeLabel.length() > 1) {
			shortenedLabel = true;
			planeLabel = planeLabel.substring(0, planeLabel.length()-1);
			stringWidth = g2d.getFontMetrics().stringWidth(planeLabel + "......");
		}

		if(shortenedLabel) planeLabel += "...";

		g2d.setColor(colourScheme.obstacleLabel);
		
		if(drawLabels)
			g2d.drawChars(planeLabel.toCharArray(), 0, planeLabel.length(), obX + obLength/2 - stringWidth/2, obY + obWidth/2 + fontHeight/2);

		//Reset font
		g2d.setFont(gFont);
	}

	private void drawMeasurement(Graphics2D g2d, float scale, int measurementLength, int arrowX, int arrowY, int angle, String identifier, int extrapolation1, int extrapolation2) {
		double angleR = angle * Math.PI / 180;
		int adjustedLength = (int)(scale* measurementLength);
		int x = (int)(Math.sin(-angleR) * adjustedLength / 2);
		int y = (int)(Math.cos(-angleR) * adjustedLength / 2);

		g2d.setColor(colourScheme.labels);
		/*Draw main line*/
		g2d.drawLine(arrowX - x, arrowY - y, arrowX + x, arrowY + y);

		/*Draw Arrow Heads*/
		double headAngleR = (angle + 45) * Math.PI / 180;
		int headLength = Math.max((int)(scale* 1500 * 0.025), (int)(adjustedLength * 0.025));
		int headX = (int)(Math.sin(headAngleR) * headLength);
		int headY = (int)(Math.cos(headAngleR) * headLength);
		g2d.drawLine(arrowX - x, arrowY - y, arrowX - x - headX, arrowY - y + headY);
		g2d.drawLine(arrowX + x, arrowY + y, arrowX + x + headX, arrowY + y - headY);

		headAngleR = -(angle + 45) * Math.PI / 180;
		headX = (int)(Math.cos(headAngleR) * headLength);
		headY = (int)(Math.sin(headAngleR) * headLength);

		g2d.drawLine(arrowX - x, arrowY - y, arrowX - x + headX, arrowY - y - headY);
		g2d.drawLine(arrowX + x, arrowY + y, arrowX + x - headX, arrowY + y + headY);

		/*Draw label*/
		Font gFont = g2d.getFont();
		g2d.setFont(new Font(gFont.getFontName(), gFont.getStyle(), (int)(scale*80)));
		String stringData = "";
		
		if(labelMeasurements)
			stringData += new String(identifier != null && !identifier.isEmpty() ? identifier : "");
		if(labelMeasurements && displayDistancesOnMeasurements)
			stringData += identifier != null && !identifier.isEmpty() ? ": " : "";
		if(displayDistancesOnMeasurements)
			stringData += new String(measurementLength + "m");
		
		g2d.drawChars(stringData.toCharArray(), 0, stringData.length(), arrowX - g2d.getFontMetrics().stringWidth(stringData)/2, arrowY-2);

		/*Draw extrapolation lines*/
		int e1x = -(int)(Math.cos(-angleR) * extrapolation1);
		int e1y = -(int)(Math.sin(-angleR) * extrapolation1);
		g2d.drawLine(arrowX - x, arrowY - y, arrowX - x + e1x, arrowY - y - e1y);

		int e2x = -(int)(Math.cos(-angleR) * extrapolation2);
		int e2y = -(int)(Math.sin(-angleR) * extrapolation2);
		g2d.drawLine(arrowX + x, arrowY + y, arrowX + x + e2x, arrowY + y - e2y);

		//Reset font
		g2d.setFont(gFont);
	}

	public void drawSideView(Graphics2D g2d, int width, int height) {
		//Pan Limit Checks
		int maxPanX = Math.abs((int)((this.zoom * width) - width)/2);
		int maxPanY = Math.abs((int)((this.zoom * height) - height)/2);

		if(sidePanX > maxPanX)
			sidePanX = maxPanX;
		if(sidePanX < -maxPanX)
			sidePanX = -maxPanX;
		if(sidePanY > maxPanY)
			sidePanY = maxPanY;
		if(sidePanY < -maxPanY)
			sidePanY = -maxPanY;
		
		//Set Background Colour
		g2d.setColor(colourScheme.background);
		g2d.fillRect(0, 0, width, height);
		
		g2d.setColor(colourScheme.labels);
		
		if (controller.getSelectedLogicalRunway() == null) {
			return;
		}
		LogicalRunway lrw = controller.getSelectedLogicalRunway();
		boolean reverse = !controller.lowAngleRunway;
		Runway runway = controller.selectedRunway;
		int windowHeight = height/2 + sidePanY;
		//Draw Runway Info
		g2d.setFont(new Font(g2d.getFont().getFontName(), Font.PLAIN, 20));
		int totalRunwayLength = Math.max(runway.shortAngleLogicalRunway.tora, runway.longAngleLogicalRunway.tora) +
				Math.max(runway.shortAngleLogicalRunway.stopwayLength, runway.shortAngleLogicalRunway.clearwayLength) +
				Math.max(runway.longAngleLogicalRunway.stopwayLength, runway.longAngleLogicalRunway.clearwayLength);
		float scale = (0.8F * width / totalRunwayLength) * zoom;

		//Drawing Values
		int drawLda = (int) (lrw.lda * scale);
		int drawTora = (int) (lrw.tora * scale);
		int drawStopwayLength = (int) (lrw.stopwayLength * scale);
		int drawClearwayLength = (int) (lrw.clearwayLength * scale);
		LogicalRunway otherLR = !controller.lowAngleRunway ? runway.shortAngleLogicalRunway : runway .longAngleLogicalRunway;
		int drawOtherStopwayLength = (int) (otherLR.stopwayLength * scale);
		int drawOtherClearwayLength = (int) (otherLR.clearwayLength * scale);
		int drawLEFT = width/2 - (int)(scale * totalRunwayLength /2) + Math.max(drawOtherStopwayLength, drawOtherClearwayLength) + (sidePanX * (reverse ? -1 : 1));

		//Draw Runways
		drawSimpleRect(g2d, drawLEFT, windowHeight, drawTora, 15, reverse, colourScheme.runwaySide, width/2);
		drawSimpleRect(g2d, drawLEFT + drawTora, windowHeight, drawStopwayLength, 10, reverse, colourScheme.stopwaySide, width/2);
		drawSimpleRect(g2d, drawLEFT + drawTora, windowHeight, drawClearwayLength, 5, reverse, colourScheme.clearwaySide, width/2);
		drawSimpleRect(g2d, drawLEFT - drawOtherStopwayLength, windowHeight, drawOtherStopwayLength, 10, reverse, colourScheme.stopwaySide, width/2);
		drawSimpleRect(g2d, drawLEFT - drawOtherClearwayLength, windowHeight, drawOtherClearwayLength, 5, reverse, colourScheme.clearwaySide, width/2);

		int drawLDALEFT = drawLEFT;


		int lda, tora, asda, toda, stopway, clearway;
		Obstacle obstacle = controller.selectedObstacle; //Can be null
		if (obstacle == null) {
			lda = lrw.lda;
			tora = lrw.tora;
			asda = lrw.asda;
			toda = lrw.toda;
			stopway = lrw.stopwayLength;
			clearway = lrw.clearwayLength;
			drawLDALEFT += (drawTora - drawLda);
		} else {
			float heightScale = 2f;
			int drawObstacleHeight = (int) (obstacle.height * heightScale);
			int drawObstacleXPos = (int) (obstacle.distanceFromLowAngleEndOfRunway * scale);
			int drawObstacleLength = (int) (obstacle.length * scale);
			int ALSWidth = (int) (obstacle.height * 50 * scale);
			boolean left = controller.towardsSelectedLR;
			if (!controller.lowAngleRunway) {
				left = !left;
			}
			drawObstacle(g2d, drawLEFT + drawObstacleXPos, windowHeight - drawObstacleHeight, drawObstacleLength, drawObstacleHeight, reverse, colourScheme.obstacle, width/2, left, ALSWidth, obstacle.height);
			ArrayList<Integer> newThreshold = controller.recalculatedValues;
			tora = newThreshold.get(0);
			toda = newThreshold.get(1);
			asda = newThreshold.get(2);
			lda = newThreshold.get(3);
			stopway = asda - tora;
			clearway = toda - tora;
		}

		int drawLEFTOLD = drawLEFT;
		g2d.setColor(colourScheme.labels);
		//TODO:: draw displaced threshold?
		if (!controller.towardsSelectedLR) {
			int diff = drawTora - (int) (tora * scale);
			drawLEFT += diff;
			drawLDALEFT += drawTora - (int) (lda * scale);
		}


		drawSimpleMeasurement(g2d, drawLDALEFT, -80, lda, scale, "LDA", reverse, width/2, windowHeight);
		drawSimpleMeasurement(g2d, drawLEFT, -120, tora, scale, "TORA", reverse, width/2, windowHeight);
		drawSimpleMeasurement(g2d, drawLEFT, -160, asda, scale, "ASDA", reverse, width/2, windowHeight);
		drawSimpleMeasurement(g2d, drawLEFT, -200, toda, scale, "TODA", reverse, width/2, windowHeight);
		drawSimpleMeasurement(g2d, drawLEFTOLD + drawTora, 50, stopway, scale, "Stopway", reverse, width/2, windowHeight);
		drawSimpleMeasurement(g2d, drawLEFTOLD + drawTora, 90, clearway, scale, "Clearway", reverse, width/2, windowHeight);

		if(drawLegend)
			drawLegend(g2d, width, height, false);
		
		drawCompassAndDirection(g2d, runway, width, height, false);
	}

	private void drawSimpleRect(Graphics2D g2d, int x, int y, int width, int height, boolean reverse, Color colour, int centreOfRunway) {
		if (width == 0) {
			return;
		}
		if (reverse) {
			x += 2*(centreOfRunway - x) - width;
		}
		g2d.setColor(colour);
		g2d.fillRect(x, y, width, height);
//		g2d.setColor(colourScheme.obstacleBorder);
//		g2d.drawRect(x, y, width, height);
	}

	private void drawSimpleMeasurement(Graphics2D g2d, int xPos, int height, int length, float scale, String label, boolean reverse, int centreOfRunway, int windowHeight) {
		if (length == 0) {
			return;
		}
		int scaleLength = (int) (length * scale);
		if (reverse) {
			xPos += 2*(centreOfRunway - xPos) - scaleLength;
		}
		int runwayYPos = windowHeight + 15;
		int startX = xPos;
		int endX = xPos + scaleLength;
		int startY = runwayYPos - height;
		g2d.drawLine(startX, startY, endX, startY);
		g2d.drawLine(startX, runwayYPos, startX, startY);
		g2d.drawLine(startX, startY, startX + 10, startY + 10);
		g2d.drawLine(startX, startY, startX + 10, startY -10);
		g2d.drawLine(endX, startY, endX - 10, startY + 10);
		g2d.drawLine(endX, startY, endX - 10, startY - 10);
		g2d.drawLine(endX, runwayYPos, endX, startY);
		
		
		String measurementText = "";
		
		if(labelMeasurements)
			measurementText += new String(label != null && !label.isEmpty() ? label : "");
		if(labelMeasurements && displayDistancesOnMeasurements)
			measurementText += label != null && !label.isEmpty() ? ": " : "";
		if(displayDistancesOnMeasurements)
			measurementText += new String(length + "m");
		
		int textWidth = g2d.getFontMetrics().stringWidth(measurementText);

		int textstartX = startX + (scaleLength - textWidth) / 2;
		g2d.drawString(measurementText, textstartX, startY - 2);
	}

	private void drawObstacle(Graphics2D g2d, int x, int y, int width, int height, boolean reverse, Color colour, int centreOfRunway, boolean left, int ALSWidth, int obstacleHeight) {
		if (width == 0) {
			return;
		}
		if (reverse) {
			x += 2*(centreOfRunway - x) - width;
		}
		g2d.setColor(colour);
		g2d.fillRect(x, y, width, height);
		g2d.setColor(colourScheme.obstacleBorder);
		g2d.drawRect(x, y, width, height);
		if (left) {
			g2d.drawLine(x, y, x - ALSWidth, y + height);
		} else {
			g2d.drawLine(x + width, y, x + width + ALSWidth, y + height);
		}
		g2d.drawString("Obstacle Height: " + obstacleHeight + "m", x, y - 15);
	}
}