package UI;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BoxLayout;

import Application.Controller;
import Model.Draw;

@SuppressWarnings("serial")
public class SideViewPanel extends PannablePanel
{
	Draw drawModule;
	Controller CONTROLLER;
	
	public SideViewPanel(Draw drawModule, Controller controller) 
	{
		super(drawModule);
		this.CONTROLLER = controller;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.drawModule = drawModule;
	}
	
	@Override
	protected void diffFunction(int diffX, int diffY) {
		CONTROLLER.adjustSidePan(diffX, diffY);
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{
		drawModule.drawSideView((Graphics2D) g, this.getWidth(), this.getHeight());
	}
}
