package UI;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
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
		
		/* Vertical Spacer */
		add(Box.createRigidArea(new Dimension(0, 50)));
		
		/* Horizontal Line */
		add(new JSeparator(SwingConstants.HORIZONTAL));
		
		/* Test label as text output */
		JLabel test1 = new JLabel("Hello World");
		test1.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(test1);
		
		/* Horizontal Line */
		add(new JSeparator(SwingConstants.HORIZONTAL));
	}
}
