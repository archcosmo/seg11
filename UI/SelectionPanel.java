package UI;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Application.Controller;

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
		//Add handler
		runwaySelection.addComponent(runwayDropdown);
		add(runwaySelection);
		
		/* Horizontal Line */
		add(new JSeparator(SwingConstants.HORIZONTAL));
		
		/* obsacle Selection and Dropdown Menu : No Handler yet */
		MenuItem obsacleSelection = new MenuItem("Obstacle : ");
		JComboBox<String> obsacleDropdown = new JComboBox<String>();
		//Add handler
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
		add(xPosSelection);
		add(yPosSelection);
		
		/* Horizontal Line */
		add(new JSeparator(SwingConstants.HORIZONTAL));
		
		/* Vertical Spacer */
		add(Box.createRigidArea(new Dimension(0, 200)));
	}
}
