package core;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TopViewPanel extends JPanel 
{
	Draw drawingModule;
	
	public TopViewPanel(Draw drawingModule) 
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		/* Vertical Spacer */
		add(Box.createRigidArea(new Dimension(0, 50)));
		
		JLabel init1 = new JLabel("Nothing to show here!");
		JLabel init2 = new JLabel("System needs to be configured with things to draw. Use \"help\" or \"help walkthrough\" for more information");
		add(init1);
		add(init2);
		
		this.drawingModule = drawingModule;
	}
	
	public void updateUI(BufferedImage i)
	{
		setLayout(new BorderLayout());
		JLabel picLabel = new JLabel(new ImageIcon(i));
		add(picLabel, BorderLayout.CENTER);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		this.drawingModule.drawTopView((Graphics2D)g, this.getWidth(), this.getHeight());
	}
}
