package UI;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

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
		
		this.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
				CONTROLLER.setTopMousePos(e.getX(), e.getY());
			}

			public void mouseDragged(MouseEvent e) {}
		});
		
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
