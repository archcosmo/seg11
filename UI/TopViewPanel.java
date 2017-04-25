package UI;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BoxLayout;

import Application.Controller;
import Model.Draw;

@SuppressWarnings("serial")
public class TopViewPanel extends PannablePanel 
{
	Controller CONTROLLER;
	Draw drawingModule;
	
	public TopViewPanel(Draw drawingModule, Controller controller) 
	{
		super(drawingModule);
		this.CONTROLLER = controller;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.drawingModule = drawingModule;
		
	}

	@Override
	protected void diffFunction(int diffX, int diffY) {
		CONTROLLER.adjustTopPan(diffX, diffY);
	}

	@Override
	public void paintComponent(Graphics g) 
	{
		this.drawingModule.drawTopView((Graphics2D)g, this.getWidth(), this.getHeight());
	}
}
