package UI;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Application.Controller;
import Model.Airport;

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
		
		/* TODO: If num airports == 0; Only allow new airport*/
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
		for (Airport a: CONTROLLER.getAirports()) 
		{
			airportDropdown.addItem(a.getName());
		}
		airportSelection.addComponent(airportDropdown);
		add(airportSelection);
		
		/* Confirmation button */
		JButton selectAirport = new JButton("Use this airport");
		selectAirport.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(selectAirport);
		selectAirport.addActionListener( new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					System.err.println("Airport Selected : "+ (String) airportDropdown.getSelectedItem());
					CONTROLLER.selectAirport((String) airportDropdown.getSelectedItem());
			}});
		
		/* Vertical Spacer */
		add(Box.createRigidArea(new Dimension(0, 50)));
		
		/* Add a newAirport option */
		MenuItem newAirport = new MenuItem("or create and use a new airport with name : ");
		JTextField newAirportName = new JTextField(" ");
		newAirportName.setPreferredSize(new Dimension(200, 20));
		newAirport.add(newAirportName);
		add(newAirport);
		
		/* Confirmation button */
		JButton makeAirport = new JButton("Create and use this airport");
		makeAirport.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(makeAirport);
		/* TODO: Add button handler */
		
		/* Vertical Spacer */
		add(Box.createRigidArea(new Dimension(0, 400)));
	}
}
