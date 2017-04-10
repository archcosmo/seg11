package UI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Application.Controller;
import Model.Runway;

@SuppressWarnings("serial")
public class SelectionPanel extends JPanel
{
	Controller CONTROLLER;
	
	public SelectionPanel(Controller c) 
	{
		CONTROLLER = c;
		initPanel();
	}

	private void initPanel() 
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(300, 0));
		addComponents();
	}

	/* Create menu */
	private void addComponents() 
	{
		/* Vertical Spacer */
		add(Box.createRigidArea(new Dimension(0, 50)));
		
		/* Runway Selection and Dropdown Menu : No Handler yet */
		MenuItem runwaySelection = new MenuItem("Runway : ");
		JComboBox<String> runwayDropdown = new JComboBox<String>();
		for (Runway r: CONTROLLER.getRunways()) 
		{
			runwayDropdown.addItem(r.getName());
		}
		runwayDropdown.addActionListener( new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("Runway Selected : "+ (String) runwayDropdown.getSelectedItem());
				CONTROLLER.selectRunway((String) runwayDropdown.getSelectedItem());
		}});
		runwaySelection.addComponent(runwayDropdown);
		add(runwaySelection);
		
		/* Threshold selector */
		MenuItem angleSelection = new MenuItem("Threshold Angle : ");
		JComboBox<String> angleDropdown = new JComboBox<String>();
		angleDropdown.addItem("Small Angle");
		angleDropdown.addItem("Large Angle");
		angleDropdown.addActionListener( new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				CONTROLLER.setRunwayAngle((String) angleDropdown.getSelectedItem());
				System.out.println("Angle Threshold Selected : "+ (String) angleDropdown.getSelectedItem());
		}});
		angleSelection.add(angleDropdown);
		add(angleSelection);
		
		/* Horizontal Line */
		add(new JSeparator(SwingConstants.HORIZONTAL));
		
		/* Obstacle Selection and Dropdown Menu */
		MenuItem obsacleSelection = new MenuItem("Obstacle : ");
		JComboBox<String> obsacleDropdown = new JComboBox<String>();
		//TODO: Add handler
		obsacleSelection.addComponent(obsacleDropdown);
		add(obsacleSelection);
		
		/* Obstacle position selection */
		MenuItem xPosSelection = new MenuItem("Horizontal Displacement : ");
		MenuItem yPosSelection = new MenuItem("Vertical Displacement : ");
		JTextField xPosInput = new JTextField("0");
		JTextField yPosInput = new JTextField("0");
		xPosInput.setPreferredSize(new Dimension(50, 20));
		yPosInput.setPreferredSize(new Dimension(50, 20));
		xPosSelection.add(xPosInput);
		yPosSelection.add(yPosInput);
		//TODO: Add BOTH handlers
		add(xPosSelection);
		add(yPosSelection);
		
		/* Horizontal Line */
		add(new JSeparator(SwingConstants.HORIZONTAL));
		
		/* Vertical Spacer */
		add(Box.createRigidArea(new Dimension(0, 200)));
	}
}
