package core;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SideViewPanel extends JPanel 
{
	public SideViewPanel() 
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		/* Vertical Spacer */
		add(Box.createRigidArea(new Dimension(0, 50)));
		
		JLabel init1 = new JLabel("Nothing to show here!");
		JLabel init2 = new JLabel("Use 'draw' command to get input");
		JLabel init3 = new JLabel("(You will need to have selected an airport and runway before you do this)");
		add(init1);
		add(init2);
		add(init3);
	}
	
	public void updateUI(BufferedImage i)
	{
		if (i != null) 
		{
			setLayout(new BorderLayout());
			JLabel picLabel = new JLabel(new ImageIcon(i));
			add(picLabel, BorderLayout.CENTER);
		}
		else
		{
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			/* Vertical Spacer */
			add(Box.createRigidArea(new Dimension(0, 50)));
			
			JLabel init1 = new JLabel("Error: Draw Command Failed");
			JLabel init2 = new JLabel("Make sure you have a runway selected before using draw");
			add(init1);
			add(init2);
		}
	}
}
