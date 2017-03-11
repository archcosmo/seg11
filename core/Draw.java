package core;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Draw 
{
	
	Model model;
	
	public Draw(Model model) {
		this.model = model;
	}

	public void drawTopView(Graphics2D g2d) {
	}

	public void drawSideView(Graphics2D g2d) {
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
}
