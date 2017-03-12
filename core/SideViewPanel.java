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
public class SideViewPanel extends JPanel 
{
	Draw drawModule;
	
	public SideViewPanel(Draw drawModule) 
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		/* Vertical Spacer */
		add(Box.createRigidArea(new Dimension(0, 15)));
		
		JLabel init1 = new JLabel("Nothing to show here!");
		JLabel init2 = new JLabel("Use 'draw' command to get input");
		JLabel init3 = new JLabel("(You will need to have selected an airport and runway before you do this)");
		add(init1);
		add(init2);
		add(init3);
		
		this.drawModule = drawModule;
	}
	
	public void updateUI(BufferedImage i)
	{
		setLayout(new BorderLayout());
		//TODO:: how to scale and position image
		JLabel picLabel = new JLabel(new ImageIcon(i));
		add(picLabel, BorderLayout.CENTER);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		drawModule.drawSideView((Graphics2D) g);
	}
}
