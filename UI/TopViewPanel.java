package UI;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import Model.Draw;

@SuppressWarnings("serial")
public class TopViewPanel extends JPanel 
{
	Draw drawingModule;
	
	public TopViewPanel(Draw drawingModule) 
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.drawingModule = drawingModule;
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{
		this.drawingModule.drawTopView((Graphics2D)g, this.getWidth(), this.getHeight());
	}
}
