package UI;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import Model.Draw;

@SuppressWarnings("serial")
public class SideViewPanel extends JPanel 
{
	Draw drawModule;
	
	public SideViewPanel(Draw drawModule) 
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.drawModule = drawModule;
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{
		drawModule.drawSideView((Graphics2D) g, this.getWidth(), this.getHeight());
	}
}
