package UI;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Application.Controller;

@SuppressWarnings("serial")
public class AirportSelectPanel extends JPanel {
	
	Controller CONTROLLER;
	//Airport list
	
	public AirportSelectPanel(Controller c) 
	{
		CONTROLLER = c;
		initPanel();
	}

	private void initPanel() 
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		addComponents();
	}

	/* Create menu */
	private void addComponents() 
	{
		/* Vertical Spacer */
		add(Box.createRigidArea(new Dimension(0, 200)));
		
		/* Intro label */
		JLabel intro = new JLabel("Welcome to " + Controller.TITLE);
		intro.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(intro);
		
		/* Vertical Spacer */
		add(Box.createRigidArea(new Dimension(0, 100)));
		
		/* Airport Selection and Dropdown Menu : No Handler yet */
		MenuItem airportSelection = new MenuItem("Select an Airport : ");
		JComboBox<String> airportDropdown = new JComboBox<String>();
		//Add handler
		airportSelection.addComponent(airportDropdown);
		add(airportSelection);
		
		/* Confirmation button */
		JButton selectAirport = new JButton("Use this airport");
		selectAirport.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(selectAirport);
		/* Add button handler here */
		
		/* Vertical Spacer */
		add(Box.createRigidArea(new Dimension(0, 50)));
		
		/* Add a newAirport option */
		MenuItem newAirport = new MenuItem("or create and use a new airport");
		JTextField newAirportName = new JTextField("$ New Airport Name");
		newAirport.add(newAirportName);
		add(newAirport);
		//Add handler
		
		/* Confirmation button */
		JButton makeAirport = new JButton("Create and use this airport");
		makeAirport.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(makeAirport);
		/* Add button handler here */
		
		/* Vertical Spacer */
		add(Box.createRigidArea(new Dimension(0, 400)));
	}
}
