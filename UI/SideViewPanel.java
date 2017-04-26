package UI;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

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
		
		this.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
				CONTROLLER.setSideMousePos(e.getX(), e.getY());
			}

			public void mouseDragged(MouseEvent e) {}
		});
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
